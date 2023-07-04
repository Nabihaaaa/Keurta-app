package com.example.seccraft_app.screens.aktivitas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.Collection.portofolio.DataPortofolio
import com.example.seccraft_app.Collection.portofolio.UserLikePortofolio
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataAktivitasModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private var likePortofolioRegistration: ListenerRegistration? = null


    val dataPortofolio = mutableStateListOf<DataPortofolio>()
    val likePortofolio = mutableStateListOf<DataPortofolio>()

    init {
        getDataPortofolio()
        //listenLikePortofolio()
    }

    private fun getDataPortofolio() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val data = mutableListOf<DataPortofolio>()

                val querySnapshotUser =
                    firestore.collection("users/$idCurrentUser/likePortofolio").whereEqualTo("like", true).get().await()

                querySnapshotUser.toObjects(UserLikePortofolio::class.java).forEach {
                    val querySnapshotPortofolio =
                        firestore.document("portofolio/${it.idPortofolio}").get().await()

                    querySnapshotPortofolio.toObject(DataPortofolio::class.java)
                        ?.let { it1 -> data.add(it1) }
                }

                dataPortofolio.addAll(data)

            } catch (e: Exception) {
                // Handle error
            }
        }
    }

//    private fun listenLikePortofolio() {
//        likePortofolioRegistration = firestore.collection("portofolio")
//            .addSnapshotListener { querySnapshot, e ->
//                if (e != null) {
//                    // Handle error
//                    return@addSnapshotListener
//                }
//
//                querySnapshot?.documents?.forEach { documentSnapshot ->
//                    val portofolio = documentSnapshot.toObject(DataPortofolio::class.java)
//                    portofolio?.let {
//                        if (!likePortofolio.contains(portofolio)) {
//                            likePortofolio.add(portofolio)
//                        }
//                    }
//                }
//            }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        likePortofolioRegistration?.remove()
//    }
}
