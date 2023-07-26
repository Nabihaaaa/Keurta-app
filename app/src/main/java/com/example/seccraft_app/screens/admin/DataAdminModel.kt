package com.example.seccraft_app.screens.admin

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.user.DataRegistrasiPaguyuban
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataAdminModel : ViewModel() {
    val firestore = FirebaseFirestore.getInstance()
    val listPagyuban = mutableStateListOf<DataRegistrasiPaguyuban>()

    init {
        viewModelScope.launch {
            getDataPaguyuban()
        }
    }

    suspend fun getDataPaguyuban() {
        val query = firestore.collection("dataRegistrasiPaguyuban").get().await()
        val data = query.toObjects(DataRegistrasiPaguyuban::class.java)
        listPagyuban.addAll(data)
    }

}