package com.adrc95.punkappsample.ui.beerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrc95.domain.usecase.FilterBeers
import com.adrc95.domain.usecase.GetBeers
import com.adrc95.domain.usecase.RefreshBeers
import com.adrc95.punkappsample.ui.beerlist.mapper.toDisplayModel
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel
import com.adrc95.punkappsample.ui.beerlist.model.PageContentDisplayModel
import com.adrc95.punkappsample.ui.beerlist.state.BeerListUiState
import com.adrc95.punkappsample.ui.beerlist.state.BeerListViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

    private val currentPage = MutableStateFlow(1)
    private val query = MutableStateFlow("")
    private val isRefreshing = MutableStateFlow(false)

    val state: StateFlow<BeerListUiState> = currentPage.flatMapLatest { page ->
        combine(
            query,
            isRefreshing,
            getBeers(page),
        ) { query, isRefreshing, beers ->
            BeerListUiState(
                query = query,
                pageContent = PageContentDisplayModel(
                    currentPage = page,
                    isLoading = isRefreshing,
                    beers = filterBeers(beers, query).map { it.toDisplayModel() },
                ),
            )
        }.onStart {
            emit(
                BeerListUiState(
                    query = query.value,
                    pageContent = PageContentDisplayModel(
                        currentPage = page,
                        isLoading = true,
                        beers = emptyList(),
                    ),
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BeerListUiState(),
    )

    fun onLoadMore(page: Int) {
        currentPage.value = page
    }

    fun onBeerClicked(beer: BeerDisplayModel) {
        _events.tryEmit(BeerListViewEvent.NavigateToBeerDetail(beer.id))
    }

    fun onRefreshBeers() {
        isRefreshing.value = true
        viewModelScope.launch {
            val error = refreshBeers(currentPage.value, true)
            isRefreshing.value = false
            if (error != null) {
                _events.tryEmit(BeerListViewEvent.ShowError)
            }
        }
    }

    fun onQueryTextChange(query: String?) {
        val normalizedQuery = query.orEmpty()
        this.query.value = normalizedQuery
        _events.tryEmit(BeerListViewEvent.EnableEndlessScroll(normalizedQuery.isEmpty()))
    }
}
