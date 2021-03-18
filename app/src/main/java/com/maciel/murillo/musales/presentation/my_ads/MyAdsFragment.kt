package com.maciel.murillo.musales.presentation.my_ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentMyAdsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyAdsFragment : Fragment() {

    private val myAdsViewModel: MyAdsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMyAdsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = myAdsViewModel
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
        getMyAdsError.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, R.string.data_error_standard_read_error, Toast.LENGTH_SHORT).show()
        })

        navigateToRegisterAd.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(MyAdsFragmentDirections.goToRegisterAdFrag())
        })
    }
}