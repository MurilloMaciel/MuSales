package com.maciel.murillo.musales.presentation.register_ad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.databinding.FragmentRegisterAdBinding
import com.maciel.murillo.musales.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterAdFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val registerAdViewModel: RegisterAdViewModel by viewModel()

    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentRegisterAdBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = registerAdViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.onShowRegisterAdScreen()
    }
}