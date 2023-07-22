package com.example.seccraft_app.screens.kursus

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieResult
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.collection.kursus.DataKontenKursus
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KontenKursusModel(idKursus : String) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    var dataKursus = mutableStateOf(DataKursus())
    var kontenKursus = mutableStateListOf<DataKontenKursus>()

    init {
        getDataKursus(idKursus)
        getKontenKursus(idKursus)
    }

    private fun getDataKursus(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentSnapshot = firestore.document("kursus/$id").get().await()
                val data = documentSnapshot.toObject(DataKursus::class.java)

                dataKursus.value = data ?: DataKursus()
            } catch (e: Exception) {
                Log.d("error", "getDataKursus: $e")
            }
        }
    }
    private fun getKontenKursus(id: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val querySnapshot =
                    firestore.collection("kursus/$id/kontenKursus").orderBy("page", Query.Direction.ASCENDING).get()
                        .await()
                val data = querySnapshot.toObjects(DataKontenKursus::class.java)
                kontenKursus.addAll(data)

            }catch (e:Exception){
                Log.d("ERRORKENAPA", "getKontenKursus: $e")
            }
        }
    }

}