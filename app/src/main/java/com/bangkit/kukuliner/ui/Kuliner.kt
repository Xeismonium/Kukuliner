package com.bangkit.kukuliner.ui

data class Kuliner(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val estimatePrice: String,
    val lat: Double,
    val lon: Double
)