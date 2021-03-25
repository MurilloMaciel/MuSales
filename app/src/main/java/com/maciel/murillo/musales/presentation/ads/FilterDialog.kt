package com.maciel.murillo.musales.presentation.ads

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.databinding.ViewSpinnerBinding
import kotlin.properties.Delegates

class FilterDialog : DialogFragment() {

    private lateinit var onClickFilter: (Int) -> Unit
    private var filterTitleResource by Delegates.notNull<Int>()
    private lateinit var filterItems: List<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme)
        builder.setTitle(getString(filterTitleResource))

        val binding = ViewSpinnerBinding.inflate(layoutInflater)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterItems).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spFilter.adapter = adapter

        builder.setView(binding.root)

        builder.setPositiveButton(getString(R.string.to_filter)) { _, _ ->
            onClickFilter.invoke(binding.spFilter.selectedItemPosition)
        }
        builder.setNegativeButton(getString(R.string.cancel), null)
        return builder.create()
    }

    companion object {
        fun show(manager: FragmentManager, onClickFilter: (Int) -> Unit, filterTitleResource: Int, filterItems: List<String>) {
            val dialog = FilterDialog()
            dialog.onClickFilter = onClickFilter
            dialog.filterTitleResource = filterTitleResource
            dialog.filterItems = filterItems
            dialog.show(manager, FilterDialog::class.java.name)
        }
    }
}