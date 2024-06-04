package com.bangkit.kukuliner.helper

import kotlin.math.*

fun toRadians(degrees: Double): Double {
    return degrees * Math.PI / 180
}

fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Radius bumi dalam km

    // Konversi derajat ke radian
    val lat1Rad = toRadians(lat1)
    val lon1Rad = toRadians(lon1)
    val lat2Rad = toRadians(lat2)
    val lon2Rad = toRadians(lon2)

    // Haversine formula
    val dlat = lat2Rad - lat1Rad
    val dlon = lon2Rad - lon1Rad
    val a = sin(dlat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dlon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}