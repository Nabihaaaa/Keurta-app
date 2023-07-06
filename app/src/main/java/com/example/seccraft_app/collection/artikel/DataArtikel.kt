package com.example.seccraft_app.collection.artikel

data class DataArtikel(
    val id : String = "",
    val image : String = "",
    val title : String = "",
    val deskripsi : String = "",
    val pembuat : String = "",
    val view : Long = 0,
    val time : Any? = null
)
