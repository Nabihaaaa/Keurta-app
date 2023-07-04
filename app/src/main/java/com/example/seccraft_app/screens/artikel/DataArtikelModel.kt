package com.example.seccraft_app.screens.artikel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.Collection.Artikel.DataArtikel
import com.example.seccraft_app.Collection.portofolio.DataPortofolio
import com.example.seccraft_app.Collection.portofolio.UserLikePortofolio
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataArtikelModel : ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()
    var dataArtikel = mutableStateListOf<DataArtikel>()

    init {
        getDataArtikel()
    }

    private fun getDataArtikel() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("artikel").get().await()
                val data = querySnapshot.toObjects(DataArtikel::class.java)
                dataArtikel.addAll(data)

            }catch (_: Exception){

            }
        }

    }

}