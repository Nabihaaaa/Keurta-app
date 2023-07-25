package com.example.seccraft_app.screens.kursus.add.addKonten

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.DataKontenKursus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AddKontenModel() : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    fun uploadDAta(
        fileUri: Uri,
        title: String,
        deskripsi: String,
        context: Context,
        navController: NavHostController,
        idKursus: String,
        page: String,
        video : Boolean
    ) {
        val id = firestore.collection("kursus").document().id
        val loc = storage.child("kursus/$idKursus/konten/$id/fileKonten_$page")
        val uploadTask = loc.putFile(fileUri)

        uploadTask.addOnSuccessListener{ taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri->
                val data = DataKontenKursus(
                    idKonten = id,
                    video = if (video) uri.toString() else "",
                    animasi = if (!video) uri.toString() else "",
                    title = title,
                    deskripsi = deskripsi,
                    page = page.toLong()
                )
                firestore.collection("kursus/$idKursus/kontenKursus")
                    .document(id)
                    .set(data)
                    .addOnSuccessListener {
                        navController.popBackStack()
                        Toast.makeText(context, R.string.data_berhasil_simpan,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}