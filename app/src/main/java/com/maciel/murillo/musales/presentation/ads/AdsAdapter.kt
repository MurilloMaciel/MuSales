package com.maciel.murillo.musales.presentation.ads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.listeners.AdListener
import com.maciel.murillo.musales.core.listeners.BindableAdapter
import com.maciel.murillo.musales.databinding.ViewAdBinding
import com.maciel.murillo.musales.domain.model.Ad

class AdsAdapter(
    private var listener: AdListener
) : RecyclerView.Adapter<AdsViewHolder>(), BindableAdapter<List<Ad>> {

    private var ads = emptyList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewAdBinding = DataBindingUtil.inflate(inflater, R.layout.view_ad, parent, false)
        return AdsViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) = holder.bind(ads[position])

    override fun getItemCount() = ads.size

    override fun updateData(data: List<Ad>) {
        this.ads = data
        notifyDataSetChanged()
    }
}