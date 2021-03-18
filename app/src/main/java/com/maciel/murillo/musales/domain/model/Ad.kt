package com.maciel.murillo.musales.domain.model

import java.io.Serializable

data class Ad(
        var id: String = "",
        var images: List<String> = mutableListOf<String>(),
        var state: String = "",
        var category: String = "",
        var title: String = "",
        var description: String = "",
        var price: String = "",
        var phone: String = "",
        var advertiserId: String = "",
) : Serializable