package com.maciel.murillo.musales.presentation.my_ads

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.databinding.ViewSpinnerBinding

class DeleteAdDialog : DialogFragment() {

    private lateinit var onClickDelete: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(requireActivity()).apply {
        setTitle(getString(R.string.want_to_delete_ad_title))
        setMessage(getString(R.string.want_to_delete_ad_description))
        setNegativeButton(getString(R.string.no), null)
        setPositiveButton(getString(R.string.yes)) { _, _ ->
            onClickDelete.invoke()
        }
    }.create()

    companion object {
        fun show(manager: FragmentManager, onClickDelete: () -> Unit) {
            val dialog = DeleteAdDialog()
            dialog.onClickDelete = onClickDelete
            dialog.show(manager, DeleteAdDialog::class.java.name)
        }
    }
}