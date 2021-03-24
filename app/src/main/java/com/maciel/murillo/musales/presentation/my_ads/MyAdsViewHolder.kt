package com.maciel.murillo.musales.presentation.my_ads

import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.musales.core.listeners.MyAdListener
import com.maciel.murillo.musales.databinding.ViewMyAdBinding
import com.maciel.murillo.musales.domain.model.Ad

class MyAdsViewHolder(
    private val binding: ViewMyAdBinding,
    private var listener: MyAdListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clContent.setOnClickListener { listener.onClickAd(adapterPosition) }
        binding.ivDelete.setOnClickListener { listener.onClickDelete(adapterPosition) }
    }

    fun bind(item: Ad) = with(itemView) {
        binding.ad = item
        binding.executePendingBindings()
    }
}