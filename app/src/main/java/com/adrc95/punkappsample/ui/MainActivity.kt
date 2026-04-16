package com.adrc95.punkappsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.adrc95.punkappsample.R
import com.adrc95.punkappsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navHostFragment.navController.graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeToolbar()
    }

    private fun initializeToolbar() = with(binding) {
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
    }
}