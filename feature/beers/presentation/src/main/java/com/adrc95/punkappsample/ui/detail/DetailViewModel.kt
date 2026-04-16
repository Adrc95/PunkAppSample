package com.adrc95.punkappsample.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrc95.domain.usecase.GetBeer
import com.adrc95.punkappsample.ui.common.Event
import com.adrc95.punkappsample.ui.di.qualifier.BeerId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @BeerId private val idBeer: Long,
    private val getBeer: GetBeer,
) : ViewModel() {

    private val _state = MutableStateFlow<DetailViewState>(DetailViewState.LoadDetails)
    val state: StateFlow<DetailViewState> = _state.asStateFlow()

    private val _error = MutableLiveData<Event<Unit>>()
    val error: LiveData<Event<Unit>> = _error

    fun onLoadDetail() {
        loadBeer()
    }

    private fun loadBeer() {
        _state.value = DetailViewState.Loading
        viewModelScope.launch {
            getBeer.invoke(idBeer).fold(ifLeft = {
                _error.value = Event(Unit)
            }, ifRight = {
                _state.value = DetailViewState.RenderBeer(it)
            })
        }
    }
}
