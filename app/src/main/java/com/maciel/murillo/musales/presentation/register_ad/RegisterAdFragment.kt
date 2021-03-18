package com.maciel.murillo.musales.presentation.register_ad

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentRegisterAdBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val REQUEST_CODE_CAMERA_PERMISSION = 1

class RegisterAdFragment : Fragment() {

    private val registerAdViewModel: RegisterAdViewModel by viewModel()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentRegisterAdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterAdBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = registerAdViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        setUpObservers()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {

        }
    }

    private fun setUpObservers() = with(registerAdViewModel) {
        formInvalid.observe(viewLifecycleOwner, {
            Toast.makeText(context, R.string.form_invalid, Toast.LENGTH_SHORT).show()
        })

        registerAdResult.observe(viewLifecycleOwner, EventObserver { success ->
            handleRegisterAdResult(success)
        })
    }

    private fun checkCameraPermission() {
        if (SDK_INT >= M && checkSelfPermission(requireContext(), CAMERA) != PERMISSION_GRANTED) {
            requestPermissions(arrayOf(CAMERA), REQUEST_CODE_CAMERA_PERMISSION)
        } else {

        }
    }

    private fun handleRegisterAdResult(success: Boolean) {
        if (success) {
            navController.popBackStack()
        } else {

        }
    }

    private fun setUpViews() = with(binding) {
        etValue.locale = Locale("pt", "BR")
    }
}