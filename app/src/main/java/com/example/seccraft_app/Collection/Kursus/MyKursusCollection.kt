package com.example.seccraft_app.Collection.Kursus

data class MyKursusCollection(
    val id: String,
    var title: String,
    var list: MutableList<MyKursusItem>
)
