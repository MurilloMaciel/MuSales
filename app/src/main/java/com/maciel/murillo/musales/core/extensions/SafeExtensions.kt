package com.maciel.murillo.musales.core.extensions

fun Char?.safe() = this ?: ""

fun String?.safe() = this ?: ""

fun Int?.safe() = this ?: 0

fun Double?.safe() = this ?: 0.0

fun Boolean?.safe() = this ?: false

fun Boolean?.safeTrue() = this ?: true

fun Long?.safe() = this ?: 0L

fun <T> List<T>?.safe() = this ?: mutableListOf<T>()

fun <K, V> Map<K, V>?.safe() = this ?: mutableMapOf<K, V>()