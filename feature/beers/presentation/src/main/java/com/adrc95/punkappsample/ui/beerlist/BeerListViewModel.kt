package com.adrc95.punkappsample.ui.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrc95.domain.model.Beer
import com.adrc95.domain.usecase.FilterBeers
import com.adrc95.domain.usecase.GetBeers
import com.adrc95.domain.usecase.RefreshBeers
import com.adrc95.punkappsample.ui.beerlist.state.BeerListUiState
import com.adrc95.punkappsample.ui.beerlist.state.BeerListViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val filterBeers: FilterBeers,
    private val getBeers: GetBeers,
    private val refreshBeers: RefreshBeers,
) : ViewModel() {

    private val _events = MutableSharedFlow<BeerListViewEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<BeerListViewEvent> = _events.asSharedFlow()

    private val _state = MutableStateFlow(BeerListUiState())
    val state: StateFlow<BeerListUiState> = _state.asStateFlow()

    private var beerJobs: Job? = null

    init {
        loadBeers(_state.value.currentPage)
    }

    fun onLoadMore(page: Int) {
        _state.update { state ->
            state.copy(
                currentPage = page,
                isLoading = true,
            )
        }
        loadBeers(page)
    }

    fun onBeerClicked(beer: Beer) {
        _events.tryEmit(BeerListViewEvent.NavigateToBeerDetail(beer))
    }

    fun onRefreshBeers() {
        _state.update { state ->
            state.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            val error = refreshBeers(_state.value.currentPage, true)
            _state.update { state ->
                state.copy(
                    isLoading = false,
                )
            }
            if (error != null) {
                _events.tryEmit(BeerListViewEvent.ShowError)
            }
        }
    }

    fun onQueryTextChange(query: String?) {
        val normalizedQuery = query.orEmpty()
        _state.update { state ->
            state.copy(
                query = normalizedQuery,
                beers = filterBeers(state.allBeers, normalizedQuery),
            )
        }
        _events.tryEmit(BeerListViewEvent.EnableEndlessScroll(normalizedQuery.isEmpty()))
    }

    private fun loadBeers(page: Int) {
        beerJobs?.cancel()
        beerJobs = viewModelScope.launch {
            getBeers(page).collect { beers ->
                _state.update { state ->
                    state.copy(
                        currentPage = page,
                        isLoading = false,
                        allBeers = beers,
                        beers = filterBeers(beers, state.query),
                    )
                }
            }
        }
    }
}
