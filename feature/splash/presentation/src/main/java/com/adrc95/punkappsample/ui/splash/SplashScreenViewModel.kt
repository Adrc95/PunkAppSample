package com.adrc95.punkappsample.ui.splash


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrc95.punkappsample.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain : LiveData<Event<Unit>> get() = _navigateToMain

    fun start() {
        _navigateToMain.value = Event(Unit)
    }
}