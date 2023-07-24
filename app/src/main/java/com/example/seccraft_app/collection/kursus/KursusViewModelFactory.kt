package com.example.seccraft_app.collection.kursus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seccraft_app.screens.kursus.KontenKursusModel

class KursusViewModelFactory(private val idKursus: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = KontenKursusModel(idKursus) as T
}