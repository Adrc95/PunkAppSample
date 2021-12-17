package com.adrc95.punkappsample.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.adrc95.punkappsample.databinding.ActivitySplashscreenBinding
import com.adrc95.punkappsample.ui.common.EventObserver
import com.adrc95.punkappsample.ui.common.view.BaseActivity
import com.adrc95.punkappsample.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashscreenBinding>() {

    companion object {
        private const val SPLASH_DURATION_IN_MILLISECONDS = 2000L
    }

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun bindView(layoutInflater: LayoutInflater): ActivitySplashscreenBinding =
        ActivitySplashscreenBinding.inflate(layoutInflater)

    override fun onActivityCreated() {
        setupObservers()
        viewModel.start()
    }

    private fun setupObservers() {
        viewModel.navigateToMain.observe(this, EventObserver {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(MainActivity.getIntent(this))
                finish()
            }, SPLASH_DURATION_IN_MILLISECONDS)
        })
    }

}