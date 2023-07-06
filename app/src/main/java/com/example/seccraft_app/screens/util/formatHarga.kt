package com.example.seccraft_app.screens.util

import java.text.NumberFormat
import java.util.*

fun formatHarga(harga: Long): String {
    val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return formatter.format(harga).toString()
}