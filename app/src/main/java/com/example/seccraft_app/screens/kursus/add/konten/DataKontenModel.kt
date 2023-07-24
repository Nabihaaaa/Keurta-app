package com.example.seccraft_app.screens.kursus.add.konten

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.collection.kursus.DataKontenKursus
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DataKontenModel(private val idKursus: String) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth
    var halamanUtama = mutableStateOf(DataKursus())
    var listKonten = mutableStateListOf<DataKontenKursus>()

    init {
        getDataHalaman(idKursus)
        getKonten(idKursus)
    }

    private fun getDataHalaman(id:String){
        firestore.document("kursus/$id").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Error getting document: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val data = snapshot.toObject(DataKursus::class.java)
                halamanUtama.value = data ?: DataKursus()
            } else {
                halamanUtama.value = DataKursus()
            }
        }
    }

    private fun getKonten(id: String){
        firestore.collection("kursus/$id/kontenKursus")
            .orderBy("page", Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error getting kontenKursus: $error")
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    val data = querySnapshot.toObjects(DataKontenKursus::class.java)
                    listKonten.clear()
                    listKonten.addAll(data)
                } else {
                    listKonten.clear()
                }
            }
    }

    suspend fun delete() {
        try {
            firestore.collection("kursus").document(idKursus).delete().await()
        } catch (e: Exception) {
            Log.e("ERROR_DELETE", "Gagal menghapus data: $e")
        }
    }

    suspend fun publikasi(){
        try {
            val docRef = firestore.collection("kursus").document(idKursus)
            val updatedData = mapOf("publikasi" to true)
            docRef.set(updatedData, SetOptions.merge()).await()
        }catch (e:Exception){
            Log.e("ERROR_DELETE", "Gagal menghapus data: $e")
        }
    }

}