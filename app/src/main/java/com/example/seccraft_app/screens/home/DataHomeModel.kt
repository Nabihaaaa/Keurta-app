package com.example.seccraft_app.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataHomeModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    val user = mutableStateOf(DataUser())
    val portofolio = mutableListOf<DataPortofolio>()
    val artikel = mutableListOf<DataArtikel>()
    val kursus = mutableListOf<DataKursus>()

    init {
        getDataUser()
        getDataPortofolio()
        getDataArtikel()
        getDataKursus()
    }

    private fun getDataKursus() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("kursus")
                    .orderBy("pengikut", Query.Direction.DESCENDING)
                    .limit(2)
                    .get().await()

                kursus.addAll(querySnapshot.toObjects(DataKursus::class.java))

            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    private fun getDataUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val querySnapshot = firestore.document("users/$idCurrentUser").get().await()
                val data = querySnapshot.toObject(DataUser::class.java)

                user.value = data ?: DataUser()

            } catch (e: Exception) {
                Log.d("error", "getDataUser: $e")
            }

        }

    }

    private fun getDataPortofolio() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("portofolio")
                    .orderBy("like", Query.Direction.DESCENDING)
                    .limit(4)
                    .get().await()

                portofolio.addAll(querySnapshot.toObjects(DataPortofolio::class.java))

            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun getDataArtikel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("artikel")
                    .orderBy("view", Query.Direction.DESCENDING)
                    .limit(3)
                    .get().await()

                artikel.addAll(querySnapshot.toObjects(DataArtikel::class.java))

            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}