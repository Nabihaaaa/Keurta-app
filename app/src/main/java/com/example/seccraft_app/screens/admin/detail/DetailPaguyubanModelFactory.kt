package com.example.seccraft_app.screens.admin.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailPaguyubanModelFactory(private val idPaguyuban : String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailPaguyubanModel(idPaguyuban) as T
}