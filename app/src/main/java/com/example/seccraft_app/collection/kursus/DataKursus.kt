package com.example.seccraft_app.collection.kursus

data class DataKursus(
    val id: String = "",
    val image: String = "",
    val title: String = "",
    val level: String = "",
    val deskripsi: String="",
    val harga: Long = 0,
    val pengikut : Long = 0,
    val pembuat : String ="",
    val time: Any? = null,
    val publikasi : Boolean = false
)


