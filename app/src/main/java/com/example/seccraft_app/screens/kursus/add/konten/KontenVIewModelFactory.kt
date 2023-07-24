package com.example.seccraft_app.screens.kursus.add.konten

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class KontenVIewModelFactory (private val idKursus: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DataKontenModel(idKursus) as T
}