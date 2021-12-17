package com.adrc95.punkappsample.ui.beerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.ui.common.Event
import com.adrc95.usecase.GetBeers
import com.adrc95.usecase.base.Invoker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor (private val invoker: Invoker,
                                             private val getBeers: GetBeers) : ViewModel() {

    companion object {
        const val ITEMS_PER_PAGE = 30
    }

    private val _state = MutableStateFlow<BeerListViewState>(BeerListViewState.LoadBeers)
    val state : StateFlow<BeerListViewState> = _state.asStateFlow()

    private val _enabledEndlessScroll = MutableLiveData<Event<Boolean>>()
    val enabledEndlessScroll : LiveData<Event<Boolean>> = _enabledEndlessScroll

    private val _error = MutableLiveData<Event<Unit>>()
    val error : LiveData<Event<Unit>> = _error

    private val _navigateToBeerDetail = MutableLiveData<Event<Beer>>()
    val navigateToBeerDetail: LiveData<Event<Beer>> = _navigateToBeerDetail

    private var refresh = false

    fun onLoadBeers() {
        loadBeers()
    }

    private fun loadBeers(page: Int = 1, refresh: Boolean = false) {
        this.refresh = refresh
        _state.value = BeerListViewState.Loading
        val params = GetBeers.Params(page, ITEMS_PER_PAGE)
        invoker.execute(getBeers, params, ::onBeersArrived)
    }

    private fun onBeersArrived(result: Either<ApiError, List<Beer>>) {
        result.fold(ifLeft = {
            _error.value = Event(Unit)
        }, ifRight = {
            _state.value = BeerListViewState.ShowBeers(it, refresh)
        })
    }

    fun onLoadMore(page: Int) {
        loadBeers(page)
    }

    fun onBeerClicked(beer: Beer) {
        _navigateToBeerDetail.value = Event(beer)
    }

    fun onRefreshBeers() {
        loadBeers(refresh = true)
    }

    fun onQueryTextChange(query : String?) {
        query?.let {
            _enabledEndlessScroll.value = Event(query.isEmpty())
            _state.value = BeerListViewState.ChangeSearchText(query)
        }

    }
}
