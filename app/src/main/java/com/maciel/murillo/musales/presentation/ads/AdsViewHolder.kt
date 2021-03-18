package com.maciel.murillo.musales.presentation.ads

import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.musales.core.helper.AdListener
import com.maciel.murillo.musales.databinding.ViewAdBinding
import com.maciel.murillo.musales.domain.model.Ad

class AdsViewHolder(
    private val binding: ViewAdBinding,
    private var listener: AdListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clContent.setOnClickListener {
            listener.onClickAd(adapterPosition)
        }
    }

    fun bind(item: Ad) = with(itemView) {
        binding.ad = item
        binding.executePendingBindings()
    }
}