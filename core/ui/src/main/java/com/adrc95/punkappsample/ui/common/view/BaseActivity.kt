package com.adrc95.punkappsample.ui.common.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VBinding : ViewBinding> : AppCompatActivity() {

    protected val binding : VBinding by lazy { bindView(layoutInflater) }

    abstract fun bindView(layoutInflater: LayoutInflater): VBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
