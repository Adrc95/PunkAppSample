package com.adrc95.punkappsample.ui.detail.state

sealed interface DetailViewEvent {
    data object ShowError : DetailViewEvent
}
