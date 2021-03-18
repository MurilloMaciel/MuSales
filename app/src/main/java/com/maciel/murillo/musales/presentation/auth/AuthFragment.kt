package com.maciel.murillo.musales.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAuthBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = authViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressedCallback()

        authViewModel.signupError.observe(viewLifecycleOwner, EventObserver { authError ->
            Toast.makeText(context, authError.resource, Toast.LENGTH_SHORT).show()
        })

        authViewModel.signupSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navigateToAds()
        })
    }

    private fun setOnBackPressedCallback() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = navigateToAds()
        })
    }

    private fun navigateToAds() {
        findNavController().navigate(AuthFragmentDirections.goToAdsFrag())
    }
}