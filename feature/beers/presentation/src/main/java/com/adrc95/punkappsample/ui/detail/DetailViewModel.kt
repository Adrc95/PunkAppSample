package com.adrc95.punkappsample.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrc95.domain.usecase.GetBeer
import com.adrc95.punkappsample.ui.detail.mapper.toDisplayModel
import com.adrc95.punkappsample.ui.detail.state.DetailUiState
import com.adrc95.punkappsample.ui.detail.state.DetailViewEvent
import com.adrc95.punkappsample.ui.di.qualifier.BeerId
import dagger.hilt.android.lifecycle.HiltViewModel
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
class DetailViewModel @Inject constructor(
    @param:BeerId private val idBeer: Long,
    private val getBeer: GetBeer,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<DetailViewEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<DetailViewEvent> = _events.asSharedFlow()

    init {
        loadBeer()
    }

    private fun loadBeer() {
        _state.update { state->
            state.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            getBeer.invoke(idBeer).fold(ifLeft = {
                _state.update { state->
                    state.copy(
                        isLoading = false,
                    )
                }
                _events.tryEmit(DetailViewEvent.ShowError)
            }, ifRight = {
                _state.update { state->
                    state.copy(
                        isLoading = false,
                        beer = it.toDisplayModel(),
                    )
                }
            })
        }
    }
}
