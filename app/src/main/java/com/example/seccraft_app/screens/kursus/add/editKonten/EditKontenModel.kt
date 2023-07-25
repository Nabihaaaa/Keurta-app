package com.example.seccraft_app.screens.kursus.add.editKonten

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.DataKontenKursus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class EditKontenModel() : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    suspend fun getKonten(idKursus: String, idKonten: String): DataKontenKursus {
        return try {
            val data =
                firestore.collection("kursus/$idKursus/kontenKursus").document(idKonten).get()
                    .await()
            data.toObject(DataKontenKursus::class.java) ?: DataKontenKursus()
        } catch (e: FirebaseFirestoreException) {
            Log.d("Err", "getKonten: $e")
            DataKontenKursus()
        }
    }

    fun uploadData(
        idKonten: String,
        fileUri: Uri,
        title: String,
        deskripsi: String,
        context: Context,
        navController: NavHostController,
        idKursus: String,
        page: String,
        video: Boolean,
        animasiKonten: String,
        videoKonten: String
    ) {
        Log.d("ISI fileuri", "uploadData: $fileUri")
        Log.d("ISI animasi", "uploadData: $animasiKonten")
        Log.d("ISI video", "uploadData: $videoKonten")

        if (fileUri.toString() == animasiKonten && videoKonten == "") {
            val data = DataKontenKursus(
                idKonten = idKonten,
                video = videoKonten,
                animasi = animasiKonten,
                title = title,
                deskripsi = deskripsi,
                page = page.toLong()
            )
            firestore.collection("kursus/$idKursus/kontenKursus")
                .document(idKonten)
                .set(data)
                .addOnSuccessListener {
                    navController.popBackStack()
                    Toast.makeText(context, R.string.data_berhasil_simpan, Toast.LENGTH_SHORT)
                        .show()
                }


        } else if (fileUri.toString() == videoKonten && animasiKonten == "") {
            val data = DataKontenKursus(
                idKonten = idKonten,
                video = videoKonten,
                animasi = animasiKonten,
                title = title,
                deskripsi = deskripsi,
                page = page.toLong()
            )
            firestore.collection("kursus/$idKursus/kontenKursus")
                .document(idKonten)
                .set(data)
                .addOnSuccessListener {
                    navController.popBackStack()
                    Toast.makeText(context, R.string.data_berhasil_simpan, Toast.LENGTH_SHORT)
                        .show()
                }


        } else {
            val loc = storage.child("kursus/$idKursus/konten/$idKonten/fileKonten_$page")
            val uploadTask = loc.putFile(fileUri)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val data = DataKontenKursus(
                        idKonten = idKonten,
                        video = if (video) uri.toString() else "",
                        animasi = if (!video) uri.toString() else "",
                        title = title,
                        deskripsi = deskripsi,
                        page = page.toLong()
                    )
                    firestore.collection("kursus/$idKursus/kontenKursus")
                        .document(idKonten)
                        .set(data)
                        .addOnSuccessListener {
                            navController.popBackStack()
                            Toast.makeText(
                                context,
                                R.string.data_berhasil_simpan,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                }
            }
        }


    }
}