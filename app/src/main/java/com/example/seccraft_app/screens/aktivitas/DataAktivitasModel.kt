package com.example.seccraft_app.screens.aktivitas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.User.UserLikeArtikel
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.collection.User.UserLikePortofolio
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataAktivitasModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()


    val dataPortofolio = mutableStateListOf<DataPortofolio>()
    var dataArtikel = mutableStateListOf<DataArtikel>()

    init {
        getDataPortofolio()
        getDataArtikel()
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

    private fun getDataArtikel() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val data = mutableListOf<DataArtikel>()

                val querySnapshotUser =
                    firestore.collection("users/$idCurrentUser/likeArtikel")
                        .whereEqualTo("like", true).get().await()

                querySnapshotUser.toObjects(UserLikeArtikel::class.java).forEach {
                    val querySnapshotPortofolio =
                        firestore.document("artikel/${it.idArtikel}").get().await()

                    querySnapshotPortofolio.toObject(DataArtikel::class.java)
                        ?.let { it1 -> data.add(it1) }
                }


                dataArtikel.addAll(data)

            }catch (_: Exception){

            }
        }

    }


}
