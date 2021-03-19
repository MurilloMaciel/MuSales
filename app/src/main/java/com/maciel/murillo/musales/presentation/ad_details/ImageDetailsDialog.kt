package com.maciel.murillo.musales.presentation.ad_details

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.databinding.ViewImageBinding

class ImageDetailsDialog : DialogFragment() {

    private lateinit var imageUrl: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ViewImageBinding.inflate(layoutInflater)
        return buildDialog(binding).apply {
            setImage(binding)
        }
    }

    private fun buildDialog(binding: ViewImageBinding): Dialog = requireContext().run {
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)
        builder.create().apply {
            setView(binding.root)
        }
    }

    private fun setImage(binding: ViewImageBinding) {
        Glide.with(binding.root.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.standard)
            .into(binding.ivImage)
    }

    companion object {
        fun show(manager: FragmentManager, imageUrl: String) {
            val dialog = ImageDetailsDialog()
            dialog.imageUrl = imageUrl
            dialog.show(manager, ImageDetailsDialog::class.java.name)
        }
    }
}