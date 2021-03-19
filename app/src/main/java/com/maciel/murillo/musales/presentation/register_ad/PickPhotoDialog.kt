package com.maciel.murillo.musales.presentation.register_ad

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.maciel.murillo.musales.databinding.ViewPickPhotoBinding

class PickPhotoDialog : DialogFragment() {

    private lateinit var onClickPickFromGallery: () -> Unit
    private lateinit var onClickPickFromCamera: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ViewPickPhotoBinding.inflate(layoutInflater)

        binding.btnPickFromCamera.setOnClickListener {
            dismiss()
            onClickPickFromCamera()
        }
        binding.btnPickFromGallery.setOnClickListener {
            dismiss()
            onClickPickFromGallery()
        }

        return buildDialog(binding.root)
    }

    private fun buildDialog(view: View): Dialog = requireContext().run {
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.create().apply {
            setView(view)
        }
    }

    companion object {
        fun show(manager: FragmentManager, onClickPickFromCamera: () -> Unit, onClickPickFromGallery: () -> Unit) {
            val dialog = PickPhotoDialog()
            dialog.onClickPickFromCamera = onClickPickFromCamera
            dialog.onClickPickFromGallery = onClickPickFromGallery
            dialog.show(manager, PickPhotoDialog::class.java.name)
        }
    }
}