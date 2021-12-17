package com.adrc95.punkappsample.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.ui.common.Event
import com.adrc95.usecase.GetBeer
import com.adrc95.usecase.base.Invoker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (savedStateHandle: SavedStateHandle,
                                           private val invoker: Invoker, private val getBeer: GetBeer) : ViewModel() {

  private val _state = MutableStateFlow<DetailViewState>(DetailViewState.LoadDetails)
  val state : StateFlow<DetailViewState> = _state.asStateFlow()

  private val _error = MutableLiveData<Event<Unit>>()
  val error : LiveData<Event<Unit>> = _error

  private val args by lazy {
    DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
  }

  fun onLoadDetail() {
    loadBeer()
  }

  private fun loadBeer() {
    _state.value = DetailViewState.Loading
    val params = GetBeer.Params(args.idBeer)
    invoker.execute(getBeer, params, ::onBeerArrived)
  }

  private fun onBeerArrived(result: Either<ApiError, Beer>) {
    result.fold(ifLeft = {
      _error.value = Event(Unit)
    }, ifRight = {
      _state.value = DetailViewState.RenderBeer(it)
    })
  }


}