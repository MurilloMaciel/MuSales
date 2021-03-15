package com.maciel.murillo.musales.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentAuthBinding
import com.maciel.murillo.musales.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val authViewModel: AuthViewModel by viewModel()

    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAuthBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = authViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.onShowAuthScreen()

        authViewModel.signupError.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it.resource, Toast.LENGTH_SHORT).show()
        })

        authViewModel.signupSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(AuthFragmentDirections.goToAdsFrag())
        })
    }
}