package com.example.seccraft_app.screens.kursus

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KontenKursusModel(private val idKursus:String) : ViewModel() {

    var dataKursus = DataKursus()

    init {
        getDataKursus(idKursus)
    }

    private fun getDataKursus(id: String) {
        val db = FirebaseFirestore.getInstance()
        try {
            db.document("kursus/$id")
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        dataKursus = snapshot.toObject(DataKursus::class.java)!!
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("ERRORKENAPA", "getDataKursus: $exception")
                }
        } catch (e: FirebaseFirestoreException) {
            Log.d("ERRORKENAPA", "getDataKursus: $e")
        }

    }

}