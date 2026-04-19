package com.adrc95.punkappsample.ui.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = checkNotNull(_binding)

    abstract fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): VBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindView(layoutInflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
