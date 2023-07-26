package com.example.seccraft_app.screens.artikel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.user.DataUser
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataArtikelModel : ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()
    var dataArtikel = mutableStateListOf<DataArtikel>()
    var role = mutableStateOf("")
    val pembuat = mutableStateOf("")

    init {
        getDataArtikel()
        userRole()
    }

    private fun userRole() {
        viewModelScope.launch(Dispatchers.IO) {
            val idUser = Firebase.auth.currentUser?.uid
            try {
                val query = firestore.document("users/$idUser").get().await()
                val data = query.toObject(DataUser::class.java)
                if (data != null) {
                    role.value = data.role
                    pembuat.value = data.name
                }

            }catch (e : Exception){
                Log.d("kenapa Error", "userRole: $e")
            }
        }
    }

    private fun getDataArtikel() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("artikel").orderBy("time", Query.Direction.DESCENDING).get().await()
                val data = querySnapshot.toObjects(DataArtikel::class.java)
                dataArtikel.addAll(data)

            }catch (_: Exception){

            }
        }

    }

}