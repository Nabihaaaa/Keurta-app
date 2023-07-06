package com.example.seccraft_app.collection.portofolio

data class DataPortofolio(
    val id: String = "",
    val idUser: String = "",
    val image: String = "",
    val judul: String = "",
    val kategori: List<Any?>? = null,
    val deskripsi: String = "",
    val like: Int = 0,
    val time: Any? = null,
    val idKursus : String= ""
)
