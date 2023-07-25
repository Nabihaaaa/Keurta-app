package com.example.seccraft_app.screens.kursus.add.konten

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.example.seccraft_app.collection.kursus.DataKontenKursus
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

    suspend fun deleteKonten(idKonten:String){
        try {
            val storageReference = FirebaseStorage.getInstance().reference
            val loc = storageReference.child("kursus/$idKursus/konten/$idKonten")
            deleteFolderContents(loc)

            firestore.collection("kursus/$idKursus/kontenKursus").document(idKonten).delete().await()

        }catch (e: FirebaseFirestoreException){
            Log.e("deleteKonten", "Gagal menghapus data: $e")
        }
    }

    suspend fun delete() {
        try {
            val storageReference = FirebaseStorage.getInstance().reference
            val loc = storageReference.child("kursus/$idKursus")

            deleteFolderContents(loc)

            val alatRef = firestore.collection("kursus/$idKursus/alat")
            deleteCollection(alatRef)

            val bahanRef = firestore.collection("kursus/$idKursus/bahan")
            deleteCollection(bahanRef)

            val kontenRef = firestore.collection("kursus/$idKursus/kontenKursus")
            deleteCollection(kontenRef)

            firestore.collection("kursus").document(idKursus).delete().await()

        } catch (e: FirebaseFirestoreException) {
            Log.e("ERROR_DELETE", "Gagal menghapus data: $e")
        }
    }

    private suspend fun deleteFolderContents(folderReference: StorageReference): Boolean {
        try {
            // Get a list of all items (files and sub-folders) in the folder
            val listResult = folderReference.listAll().await()
            val deleteTasks = mutableListOf<Task<Void>>()

            // Delete all files
            for (fileReference in listResult.items) {
                fileReference.delete().await()
            }

            // Delete all sub-folders recursively
            for (subFolderReference in listResult.prefixes) {
                deleteFolderContents(subFolderReference)
            }

            Tasks.whenAllComplete(deleteTasks).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }
    private suspend fun deleteCollection(collectionRef: CollectionReference) {
        val querySnapshot = collectionRef.get().await()

        querySnapshot.documents.forEach { document ->
            document.reference.delete().await()
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