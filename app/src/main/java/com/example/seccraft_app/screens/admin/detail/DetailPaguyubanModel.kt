package com.example.seccraft_app.screens.admin.detail

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.activity.MainActivity
import com.example.seccraft_app.collection.user.DataRegistrasiPaguyuban
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailPaguyubanModel(private val idPaguyuban : String) : ViewModel() {

    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference
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

    fun getZip(context: Context, url: String, fileName: String){
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