package com.maciel.murillo.musales.presentation.register_ad

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.extensions.log
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentRegisterAdBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.util.*

private const val REQUEST_CODE_CAMERA_PERMISSION = 1
private const val REQUEST_CODE_CAMERA = 2
private const val REQUEST_CODE_GALLERY = 3

class RegisterAdFragment : Fragment() {

    private val registerAdViewModel: RegisterAdViewModel by viewModel()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentRegisterAdBinding? = null
    private val binding get() = _binding!!

    private val images = mutableListOf<String>("", "", "")
    private var imagePositionInEdition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterAdBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = registerAdViewModel
            toolbar.setNavigationOnClickListener { navController.popBackStack() }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                handleAdImageResult(requestCode, uri)
            }
        }
    }

    private fun setUpViews() = with(binding) {
        etValue.locale = Locale("pt", "BR")
    }

    private fun setUpObservers() = with(registerAdViewModel) {
        formInvalid.observe(viewLifecycleOwner, {
            Toast.makeText(context, R.string.form_invalid, Toast.LENGTH_SHORT).show()
        })

        registerAdResult.observe(viewLifecycleOwner, EventObserver { success ->
            handleRegisterAdResult(success)
        })

        imageSelection.observe(viewLifecycleOwner, EventObserver { imagePosition ->
            imagePositionInEdition = imagePosition
            handleImageSelection()
        })

        prepareImages.observe(viewLifecycleOwner, EventObserver {
            prepareImages()
        })
    }

    private fun prepareImages() {
        val bitmaps = mutableListOf<ByteArray>()
        images.forEach { image ->
            if (image.isNotBlank()) {
                val bitmap = if (SDK_INT < P) {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, image.toUri())
                } else {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, image.toUri())
                    ImageDecoder.decodeBitmap(source)
                }
                ByteArrayOutputStream().apply {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, this)
                    bitmaps.add(this.toByteArray())
                }
            }
        }
        registerAdViewModel.onPrepareImages(bitmaps)
    }

    private fun handleAdImageResult(requestCode: Int, uri: Uri) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            handleCameraImage()
        } else if (requestCode == REQUEST_CODE_GALLERY) {
//            registerAdViewModel.onSelectImageFromGallery(uri.toString())
            images[imagePositionInEdition] = uri.toString()
            when (imagePositionInEdition) {
                0 -> binding.ivAdImage1.setImageURI(uri)
                1 -> binding.ivAdImage2.setImageURI(uri)
                2 -> binding.ivAdImage3.setImageURI(uri)
            }
        }
    }

    private fun handleCameraImage() {

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
            Toast.makeText(context, R.string.data_error_standard_save_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleImageSelection() {
        PickPhotoDialog.show(
            manager = childFragmentManager,
            onClickPickFromCamera = ::getImageFromCamera,
            onClickPickFromGallery = ::getImageFromGallery
        )
    }

    private fun getImageFromCamera() {

    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }
}