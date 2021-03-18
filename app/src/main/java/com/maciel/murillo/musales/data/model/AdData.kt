package com.maciel.murillo.musales.data.model

import com.google.firebase.firestore.PropertyName
import com.maciel.murillo.musales.data.remote.PROPERTY_CATEGORY
import com.maciel.murillo.musales.data.remote.PROPERTY_STATE
import com.maciel.murillo.musales.data.remote.PROPERTY_USER_UID
import com.maciel.murillo.musales.domain.model.Ad

data class AdData(
        var id: String = "",
        var images: List<String> = mutableListOf<String>(),
        var title: String = "",
        var description: String = "",
        var price: String = "",
        var phone: String = "",

        @get:PropertyName(PROPERTY_USER_UID)
        @set:PropertyName(PROPERTY_USER_UID)
        var advertiserId: String = "",

        @get:PropertyName(PROPERTY_STATE)
        @set:PropertyName(PROPERTY_STATE)
        var state: String = "",

        @get:PropertyName(PROPERTY_CATEGORY)
        @set:PropertyName(PROPERTY_CATEGORY)
        var category: String = "",
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
        advertiserId = advertiserId,
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
        advertiserId = advertiserId,
)