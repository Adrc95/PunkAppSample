package com.adrc95.punkappsample.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.content.Intent
import androidx.activity.viewModels
import com.adrc95.feature.splash.presentation.databinding.ActivitySplashscreenBinding
import com.adrc95.punkappsample.ui.common.EventObserver
import com.adrc95.punkappsample.ui.common.view.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashscreenBinding>() {

    companion object {
        private const val SPLASH_DURATION_IN_MILLISECONDS = 2000L
        private const val MAIN_ACTIVITY_CLASS_NAME = "com.adrc95.punkappsample.ui.MainActivity"
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
                val intent = Intent().setClassName(this, MAIN_ACTIVITY_CLASS_NAME)
                startActivity(intent)
                finish()
            }, SPLASH_DURATION_IN_MILLISECONDS)
        })
    }

}
