package com.example.seccraft_app.screens.kursus.add.halaman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HalamanUtamaModelFactory (private val idKursus: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HalamanUtamaModel(idKursus) as T
}