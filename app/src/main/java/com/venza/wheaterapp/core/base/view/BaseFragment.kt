package com.venza.wheaterapp.core.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.venza.wheaterapp.core.utils.AppPreferences
import com.venza.wheaterapp.core.utils.Extensions
import javax.inject.Inject

abstract class BaseFragment<T> : Fragment() where T: ViewDataBinding {

    @get:LayoutRes
    protected abstract val layoutResourceLayout: Int
    protected lateinit var navController: NavController
    protected lateinit var dataBinder: T
    protected lateinit var rootView: View

    @Inject
    protected lateinit var extensions: Extensions

    @Inject
    protected lateinit var appPreferences: AppPreferences
    abstract fun onFragmentCreated(dataBinder: T)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this@BaseFragment.layoutResourceLayout.let {
            dataBinder = DataBindingUtil.inflate(inflater, it, container, false)
            this@BaseFragment.onFragmentCreated(dataBinder)
            setUpViewModelStateObservers()

            rootView = dataBinder.root
            return rootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    abstract fun setUpViewModelStateObservers()

    protected fun backFragment() {
        requireActivity().onBackPressed()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

}