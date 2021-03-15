package com.maciel.murillo.musales.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val splashViewModel: SplashViewModel by viewModel()

    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.onShowSplashScreen()

        splashViewModel.checkUserAndRedirect()

        splashViewModel.navigateToAds.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(SplashFragmentDirections.goToAdsFrag())
        })

        splashViewModel.navigateToAuth.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(SplashFragmentDirections.goToAuthFrag())
        })
    }
}