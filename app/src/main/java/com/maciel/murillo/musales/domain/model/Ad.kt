package com.maciel.murillo.musales.domain.model

data class Ad(
        var id: String,
        var images: List<String> = mutableListOf<String>(),
        var state: String,
        var category: String,
        var title: String,
        var description: String,
        var price: String,
        var phone: String,
)