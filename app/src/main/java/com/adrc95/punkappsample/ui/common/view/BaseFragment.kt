package com.adrc95.punkappsample.ui.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VBinding : ViewBinding> : Fragment(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected lateinit var binding : VBinding



    abstract fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): VBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindView(layoutInflater, container)
        return binding.root
    }

    open fun onFragmentCreated() {}

    open fun initFlows() {}

    override fun onStart() {
        super.onStart()
        job = SupervisorJob()
        initFlows()
    }

    override fun onStop() {
        job.cancel()
        super.onStop()
    }

}