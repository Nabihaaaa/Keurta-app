package com.example.seccraft_app.screens.auth.registerPaguyuban

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.user.DataRegistrasiPaguyuban
import com.example.seccraft_app.collection.user.DataUser
import com.example.seccraft_app.navigation.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class RegistrasiPaguyubanModel : ViewModel() {
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val auth = Firebase.auth

    suspend fun upload(
        nama: String,
        email: String,
        password: String,
        nomor: String,
        alamat: String,
        deskripsi: String,
        suratIzin: Uri,
        buktiLain: Uri?,
        navController: NavHostController,
        context: Context,
        image: Uri
    ) {
        try {

            val id = auth.createUserWithEmailAndPassword(email, password).await().user?.uid
            var uriBukti = ""

            if (id != null) {

                val upl_surat = storage.reference.child("dataRegistrasiPaguyuban/$id/surat_izin")
                    .putFile(suratIzin).await()
                val uriSurat = upl_surat.storage.downloadUrl.await().toString()
                val upl_image = storage.reference.child("ProfileUser/$id/PhotoProfile")
                    .putFile(image).await()
                val uriImage = upl_image.storage.downloadUrl.await().toString()

                if (buktiLain != null){
                    val upl_bukti = storage.reference.child("dataRegistrasiPaguyuban/$id/bukti_lain")
                        .putFile(buktiLain).await()

                    uriBukti = upl_bukti.storage.downloadUrl.await().toString()
                }
                val dataReg = DataRegistrasiPaguyuban(id,nama,email, nomor, alamat, deskripsi, uriSurat, uriBukti, uriImage)
                val dataUser = DataUser(uriImage, nama, email, nomor, "paguyuban")

                firestore.collection("users").document(id).set(dataUser).await()
                firestore.collection("dataRegistrasiPaguyuban").document(id).set(dataReg).await()
                Toast.makeText(context, R.string.register_sc_pag, Toast.LENGTH_LONG).show()
                navController.navigate(Screens.Login.route)
            } else {
                Toast.makeText(context, R.string.register_fail, Toast.LENGTH_LONG).show()
            }
        }catch (e:FirebaseFirestoreException){
            Log.d("Err", "upload: $e")
        }


    }

}