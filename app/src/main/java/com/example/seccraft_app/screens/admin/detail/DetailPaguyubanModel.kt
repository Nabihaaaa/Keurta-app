package com.example.seccraft_app.screens.admin.detail

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.seccraft_app.R
import com.example.seccraft_app.activity.MainActivity
import com.example.seccraft_app.collection.user.DataRegistrasiPaguyuban
import com.example.seccraft_app.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailPaguyubanModel(private val idPaguyuban: String) : ViewModel() {

    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    var paguyuban = mutableStateOf(DataRegistrasiPaguyuban())

    init {
        viewModelScope.launch {
            getDataPaguyuban()
        }
    }

    suspend fun getDataPaguyuban() {
        val query = firestore.collection("dataRegistrasiPaguyuban")
            .document(idPaguyuban).get().await()

        val data = query.toObject(DataRegistrasiPaguyuban::class.java) ?: DataRegistrasiPaguyuban()
        paguyuban.value = data
    }

    suspend fun approvedPaguyuban(navController: NavController, context: Context) {
        try {
            //move data
            val queryPag = firestore.collection("dataRegistrasiPaguyuban")
                .document(idPaguyuban).get().await()
            val dataPag = queryPag.toObject(DataRegistrasiPaguyuban::class.java)!!
            firestore.collection("users/$idPaguyuban/dataPaguyuban")
                .document(idPaguyuban).set(dataPag)

            // Kirim email notifikasi ke alamat email yang di-approved

            // an intent to send an email
            val emailAddress = arrayOf(dataPag.email)
            val i = Intent(Intent.ACTION_SEND)
            i.putExtra(Intent.EXTRA_EMAIL,emailAddress)
            i.putExtra(Intent.EXTRA_SUBJECT, "Paguyuban berhasil diapproved!")
            i.putExtra(Intent.EXTRA_TEXT,"Registrasi kamu di aplikasi KEURTA sudah kami approved silahkan membuka aplikasi dan login. Terimakasih sudah bermitra bersama kami")
            i.setType("message/rfc822")

            // on the below line we are starting our activity to open email application.
            context.startActivity(Intent.createChooser(i,"Choose an Email client : "))

            //delete registrasi
            queryPag.reference.delete().await()
            //nav
            Toast.makeText(context, "Paguyuban berhasil diapproved!", Toast.LENGTH_LONG).show()
            navController.navigate(Screens.AdminScreen.route)

        } catch (e : FirebaseFirestoreException) {
            Log.d("ERR", "approvedPaguyuban: $e")
        }

    }

    fun getZip(context: Context, url: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("Downloading File")
        request.setDescription("Downloading $fileName")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

    }

    fun isWriteExternalStoragePermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestWriteExternalStoragePermission(context: Context) {
        val activity = context as? Activity
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MainActivity.PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    }


}