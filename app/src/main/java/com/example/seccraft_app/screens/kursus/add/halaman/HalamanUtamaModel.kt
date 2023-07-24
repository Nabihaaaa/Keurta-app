package com.example.seccraft_app.screens.kursus.add.halaman

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.seccraft_app.collection.kursus.DataAlatdanBahan
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class HalamanUtamaModel(private val idKursus: String) : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth

    var kursusData = mutableStateOf(DataKursus())
    var alat = mutableStateListOf<DataAlatdanBahan>()
    var bahan = mutableStateListOf<DataAlatdanBahan>()

    init {
        getDataKursus()
        getALat()
        getBahan()
    }

    private fun getDataKursus() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentSnapshot = firestore.document("kursus/$idKursus").get().await()
                val data = documentSnapshot.toObject(DataKursus::class.java)
                kursusData.value = data ?: DataKursus()
            } catch (e: Exception) {
                Log.d("error", "getDataKursus: $e")
            }
        }
    }

    private fun getBahan() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collection = firestore.collection("kursus/$idKursus/bahan").get().await()
                val data = collection.toObjects(DataAlatdanBahan::class.java)
                bahan.addAll(data)
            } catch (e: FirebaseFirestoreException) {
                Log.d("error", "getALat: $e")
            }
        }
    }

    private fun getALat() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collection = firestore.collection("kursus/$idKursus/alat").get().await()
                val data = collection.toObjects(DataAlatdanBahan::class.java)
                alat.addAll(data)
            } catch (e: FirebaseFirestoreException) {
                Log.d("error", "getALat: $e")
            }
        }
    }

    fun updateKursus(
        id: String,
        dataKursus: DataKursus,
        alat: SnapshotStateList<DataAlatdanBahan>,
        bahan: SnapshotStateList<DataAlatdanBahan>,
        imageUri: Uri,
        context: Context,
        navController: NavHostController
    ) {
        try {
            val storageReference = FirebaseStorage.getInstance().reference

            if (imageUri.toString() != kursusData.value.image) {
                val loc = storageReference.child("kursus/$id/gambar")
                val uploadTask = loc.putFile(imageUri)
                uploadTask.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        update(id, dataKursus, alat, bahan, uri, context, navController)
                    }
                }
            } else {
                update(id, dataKursus, alat, bahan, imageUri, context, navController)
            }

        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "update: $e")
        }
    }

    private fun update(
        id: String,
        dataKursus: DataKursus,
        alat: SnapshotStateList<DataAlatdanBahan>,
        bahan: SnapshotStateList<DataAlatdanBahan>,
        imageUri: Uri,
        context: Context,
        navController: NavHostController
    ) {

        val timeNow = FieldValue.serverTimestamp()
        val data = dataKursus.copy(
            id = id,
            image = imageUri.toString(),
            pembuat = auth.currentUser!!.uid,
            time = timeNow
        )
        firestore.collection("kursus/$id/alat").get().addOnSuccessListener { alt ->
            alt.forEachIndexed { index, queryA ->
                firestore.collection("kursus/$id/alat").document(queryA.id).delete()
                if (index == alt.size() - 1) {
                    firestore.collection("kursus/$id/bahan").get().addOnSuccessListener { bhn ->
                        bhn.forEachIndexed { index, queryB ->
                            firestore.collection("kursus/$id/bahan").document(queryB.id).delete()
                            if (index == bhn.size() - 1) {
                                firestore.collection("kursus").document(id)
                                    .set(data)
                                    .addOnSuccessListener {
                                        alat.forEachIndexed { index, dataAlat ->
                                            val idAlat = firestore.collection("kursus/$id/alat")
                                                .document().id
                                            firestore.collection("kursus/$id/alat").document(idAlat)
                                                .set(dataAlat).addOnSuccessListener {
                                                if (index == this.alat.size - 1) {
                                                    bahan.forEachIndexed { index, dataBahan ->
                                                        val idBahan =
                                                            firestore.collection("kursus/$id/bahan")
                                                                .document().id
                                                        firestore.collection("kursus/$id/bahan")
                                                            .document(idBahan).set(dataBahan)
                                                            .addOnSuccessListener {
                                                                if (index == this.bahan.size - 1) {
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Data Berhasil di Simpan!",
                                                                        Toast.LENGTH_LONG
                                                                    ).show()
                                                                    navController.navigate("konten_screen/$id")
                                                                }
                                                            }
                                                    }
                                                }
                                            }
                                        }

                                    }
                            }
                        }
                    }
                }
            }
        }


    }


}