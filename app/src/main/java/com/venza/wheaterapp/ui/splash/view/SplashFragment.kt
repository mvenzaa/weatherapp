package com.venza.wheaterapp.ui.splash.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.venza.wheaterapp.MainActivity
import com.venza.wheaterapp.R
import com.venza.wheaterapp.core.base.view.BaseFragment
import com.venza.wheaterapp.core.utils.AppConstants.SPLASH_TIME_OUT
import com.venza.wheaterapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class SplashFragment (override val layoutResourceLayout: Int = R.layout.fragment_splash) : BaseFragment<FragmentSplashBinding>(){

    private var param1: String? = null
    private var param2: String? = null
//    private val mViewModel: HomeViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return rootView
    }

    override fun onFragmentCreated(dataBinder: FragmentSplashBinding) {
        dataBinder.apply {
            fragment = this@SplashFragment
            lifecycleOwner = this@SplashFragment
        }


    }

    override fun setUpViewModelStateObservers() {
        print("")
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            (requireActivity() as MainActivity).mViewModel.readyToFetch.observe(requireActivity(),
                { requirements ->
                    requirements?.let {
                        if ( it["permission"] == true) {
                            navController.navigate(R.id.homeFragment)
                        } else if (it["permission"] == false) {
                            showTakePermissions()
                        }
                    }
                }
            )
        }, SPLASH_TIME_OUT)

    }

    /**
     * layout of No Internet
     */
    private fun showNoInternet(){
        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(viewNoInternet.root)
            viewNoInternet.cardTryAgain.setOnClickListener {
                extensions.openWifi()
            }
        }
    }

    /**
     * layout of No Gps
     */
    private fun showNoGps(){
        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(viewNoGps.root)
            viewNoGps.cardTryAgain.setOnClickListener {
                (requireActivity() as MainActivity).turnOnGPS()
            }
        }
    }

    /**
     * layout of Take Permissions
     */
    private fun showTakePermissions(){
        dataBinder.apply {
            viewFlipper.displayedChild = viewFlipper.indexOfChild(viewTakePermissions.root)
            viewTakePermissions.cardTryAgain.setOnClickListener {
                (requireActivity() as MainActivity).requestLocationPermission()
            }
        }
    }






}