package com.example.seccraft_app.screens.kursus.add

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.collection.kursus.DataAlatdanBahan
import com.example.seccraft_app.collection.kursus.DataKursus
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


fun UploadKursus(
    dataKursus: DataKursus,
    alat: SnapshotStateList<DataAlatdanBahan>,
    bahan: SnapshotStateList<DataAlatdanBahan>,
    imageUri: Uri,
    context: Context,
    navController: NavHostController
) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUserId = auth.currentUser!!.uid
    val id = db.collection("kursus").document().id

    val storageReference = FirebaseStorage.getInstance().reference
    val loc = storageReference.child("kursus/$id/gambar")
    val uploadTask = loc.putFile(imageUri)
    val timeNow = FieldValue.serverTimestamp()

    uploadTask.addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
            val data = dataKursus.copy(
                id = id,
                image = uri.toString(),
                pembuat = currentUserId,
                time = timeNow
            )
            db.collection("kursus").document(id)
                .set(data)
                .addOnSuccessListener {
                    alat.forEachIndexed { index, dataAlat->
                        val idAlat = db.collection("kursus/$id/alat").document().id
                        db.collection("kursus/$id/alat").document(idAlat).set(dataAlat).addOnSuccessListener {
                            if (index == alat.size-1){
                                bahan.forEachIndexed { index, dataBahan ->
                                    val idBahan = db.collection("kursus/$id/bahan").document().id
                                    db.collection("kursus/$id/bahan").document(idBahan).set(dataBahan).addOnSuccessListener {
                                        if (index == bahan.size-1){
                                            Toast.makeText(context, "Data Berhasil di Upload!", Toast.LENGTH_LONG).show()
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