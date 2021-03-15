package com.maciel.murillo.musales.data.model

import com.maciel.murillo.musales.domain.model.Category

enum class CategoryData {

    CARS,
    PROPERTIES,
    ELECTRONICS,
    FASHION,
    SPORTS,
    MUSIC,
    CHILD,
    JOBS,
}

fun Category.mapToCategoryData() = when (this) {
    Category.CARS -> CategoryData.CARS
    Category.PROPERTIES -> CategoryData.PROPERTIES
    Category.ELECTRONICS -> CategoryData.ELECTRONICS
    Category.FASHION -> CategoryData.FASHION
    Category.SPORTS -> CategoryData.SPORTS
    Category.MUSIC -> CategoryData.MUSIC
    Category.CHILD -> CategoryData.CHILD
    Category.JOBS -> CategoryData.JOBS
}