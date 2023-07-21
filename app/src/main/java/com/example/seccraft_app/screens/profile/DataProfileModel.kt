package com.example.seccraft_app.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.User.DataUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataProfileModel: ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()
    val state = mutableStateOf(DataUser())

    init {
        getDataUser()
    }

    private fun getDataUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val querySnapshot = firestore.document("users/$idCurrentUser").get().await()
                val data = querySnapshot.toObject(DataUser::class.java)

                state.value = data ?: DataUser()

            } catch (e: Exception) {
                Log.d("error", "getDataUser: $e")
            }

        }
    }

}


