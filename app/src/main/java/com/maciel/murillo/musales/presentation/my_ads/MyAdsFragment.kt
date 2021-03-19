package com.maciel.murillo.musales.presentation.my_ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.AdListener
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentMyAdsBinding
import com.maciel.murillo.musales.presentation.ads.AdsAdapter
import com.maciel.murillo.musales.presentation.ads.AdsFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyAdsFragment : Fragment() {

    private val myAdsViewModel: MyAdsViewModel by viewModel()

    private val navController by lazy { findNavController() }

    private val adapter = AdsAdapter(object : AdListener {
        override fun onClickAd(position: Int) = myAdsViewModel.onClickAd(position)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMyAdsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = myAdsViewModel
            rvAds.adapter = adapter
            toolbar.setNavigationOnClickListener { navController.popBackStack() }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    override fun onResume() {
        super.onResume()

        myAdsViewModel.onResumeScreen()
    }

    private fun setUpObservers() = with(myAdsViewModel) {
        navigateToRegisterAd.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(MyAdsFragmentDirections.goToRegisterAdFrag())
        })

        readUserUidError.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, R.string.read_user_uid_standard_error, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        })

        navigateToAdDetails.observe(viewLifecycleOwner, EventObserver { ad ->
            navController.navigate(AdsFragmentDirections.goToAdDetailsFrag(ad))
        })
    }
}