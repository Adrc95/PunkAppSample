package com.adrc95.punkappsample.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrc95.domain.usecase.GetBeer
import com.adrc95.punkappsample.ui.detail.mapper.toDisplayModel
import com.adrc95.punkappsample.ui.detail.state.DetailUiState
import com.adrc95.punkappsample.ui.detail.state.DetailViewEvent
import com.adrc95.punkappsample.ui.di.qualifier.BeerId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @param:BeerId private val idBeer: Long,
    private val getBeer: GetBeer,
) : ViewModel() {
    private val _events = MutableSharedFlow<DetailViewEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<DetailViewEvent> = _events.asSharedFlow()

    val state: StateFlow<DetailUiState> = flow {
        emit(getBeer(idBeer))
    }.transform { result ->
        result.fold(
            ifLeft = {
                _events.tryEmit(DetailViewEvent.ShowError)
                emit(DetailUiState(isLoading = false))
            },
            ifRight = {
                emit(
                    DetailUiState(
                        isLoading = false,
                        beer = it.toDisplayModel(),
                    )
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailUiState(),
    )
}
