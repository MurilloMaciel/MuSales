package com.maciel.murillo.musales.data.model

import com.maciel.murillo.musales.domain.model.Ad

data class AdData(
        var id: String,
        var images: List<String> = mutableListOf<String>(),
        var state: String,
        var category: String,
        var title: String,
        var description: String,
        var price: String,
        var phone: String,
)

fun AdData.mapToAd() = Ad(
        id = id,
        images = images,
        state = state,
        category = category,
        title = title,
        description = description,
        price = price,
        phone = phone,
)

fun Ad.mapToAdData() = AdData(
        id = id,
        images = images,
        state = state,
        category = category,
        title = title,
        description = description,
        price = price,
        phone = phone,
)