package com.maciel.murillo.musales.presentation.register_ad

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentRegisterAdBinding
import com.maciel.murillo.musales.domain.model.Ad
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
            getImageFromCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            data?.run {
                handleAdImageResult(requestCode, this)
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
    }

    private fun handleAdImageResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            data.extras?.get("data")?.takeIf { it is Bitmap }?.run {
                handleCameraImage(this as Bitmap)
            }
        } else if (requestCode == REQUEST_CODE_GALLERY) {
            data.data?.run {
                handleGalleryImage(this)
            }
        }
    }

    private fun handleCameraImage(bitmap: Bitmap) {
        prepareImage(bitmap)
        when (imagePositionInEdition) {
            0 -> binding.ivAdImage1.setImageBitmap(bitmap)
            1 -> binding.ivAdImage2.setImageBitmap(bitmap)
            2 -> binding.ivAdImage3.setImageBitmap(bitmap)
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        return if (SDK_INT < P) {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }

    private fun handleGalleryImage(uri: Uri) {
        prepareImage(getBitmapFromUri(uri))
        when (imagePositionInEdition) {
            0 -> binding.ivAdImage1.setImageURI(uri)
            1 -> binding.ivAdImage2.setImageURI(uri)
            2 -> binding.ivAdImage3.setImageURI(uri)
        }
    }

    private fun prepareImage(bitmap: Bitmap) {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, this)
            registerAdViewModel.onPrepareImage(this.toByteArray())
        }
    }

    private fun checkCameraPermission() {
        if (checkSelfPermission(requireContext(), CAMERA) != PERMISSION_GRANTED) {
            requestPermissions(arrayOf(CAMERA), REQUEST_CODE_CAMERA_PERMISSION)
        } else {
            getImageFromCamera()
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
            onClickPickFromCamera = ::checkCameraPermission,
            onClickPickFromGallery = ::getImageFromGallery
        )
    }

    private fun getImageFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }
}