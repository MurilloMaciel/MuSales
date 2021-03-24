package com.maciel.murillo.musales.presentation.my_ads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.listeners.BindableAdapter
import com.maciel.murillo.musales.core.listeners.MyAdListener
import com.maciel.murillo.musales.databinding.ViewMyAdBinding
import com.maciel.murillo.musales.domain.model.Ad

class MyAdsAdapter(
    private var listener: MyAdListener
) : RecyclerView.Adapter<MyAdsViewHolder>(), BindableAdapter<List<Ad>> {

    private var myAds = emptyList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewMyAdBinding = DataBindingUtil.inflate(inflater, R.layout.view_my_ad, parent, false)
        return MyAdsViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyAdsViewHolder, position: Int) = holder.bind(myAds[position])

    override fun getItemCount() = myAds.size

    override fun updateData(data: List<Ad>) {
        this.myAds = data
        notifyDataSetChanged()
    }
}