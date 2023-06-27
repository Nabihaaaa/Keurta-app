package com.example.seccraft_app.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.Collection.User.DataUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

class DataProfileModel: ViewModel(){
    val state = mutableStateOf(DataUser())
    init {
        getData()
    }
    private fun getData(){
        viewModelScope.launch {
            state.value = getDataProfile()
        }
    }
}

suspend fun getDataProfile(): DataUser {
    val auth = Firebase.auth
    val db = FirebaseFirestore.getInstance()
    val deferred = CompletableDeferred<DataUser>()

    try {
        db.document("users/${auth.currentUser!!.uid}")
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && snapshot.exists()) {
                    val name = snapshot.getString("name")!!
                    val image = snapshot.getString("image")!!
                    val email = snapshot.getString("email")!!
                    val number = snapshot.getString("number")!!
                    val dataUser = DataUser(image, name, email, number)
                    deferred.complete(dataUser)
                    //Log.d("isiDataUserModel", "getDataProfile: $dataUser")
                }
            }
    } catch (e: FirebaseFirestoreException) {
        Log.d("DataError", "getDataProfile: $e")
        deferred.completeExceptionally(e)
    }

    return deferred.await()
}
