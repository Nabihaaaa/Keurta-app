package com.example.seccraft_app.screens.aktivitas

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seccraft_app.collection.user.UserLikeArtikel
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.collection.user.UserLikePortofolio
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.forum.ForumCollection
import com.example.seccraft_app.collection.forum.ForumUser
import com.example.seccraft_app.collection.forum.ReplyUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataAktivitasModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()


    val dataPortofolio = mutableStateListOf<DataPortofolio>()
    var dataArtikel = mutableStateListOf<DataArtikel>()
    val dataPortolioUser = mutableStateListOf<DataPortofolio>()
    val dataForumUser = mutableStateListOf<ForumCollection>()

    init {
        getDataPortofolio()
        getDataArtikel()
        getDataPortofolioUser()
        getDataUserForum()
    }

    private fun getDataUserForum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val data = mutableListOf<ForumCollection>()

                val userForum = firestore.collection("users/$idCurrentUser/forum")
                    .get()
                    .await()
                    .toObjects(ForumUser::class.java)

                val userReply = firestore.collection("users/$idCurrentUser/replyForum")
                    .get()
                    .await()
                    .toObjects(ReplyUser::class.java)

                val foundForums = mutableSetOf<String>()

                userForum.forEach { forum->
                    val querySnapshot = firestore.document("Forum/${forum.idForum}").get().await()
                    querySnapshot.toObject(ForumCollection::class.java)?.let { data.add(it) }
                    foundForums.add(forum.idForum)
                }
                userReply.forEach { replyUser ->
                    if (replyUser.idForum !in foundForums) {
                        val query = firestore.document("Forum/${replyUser.idForum}")
                            .get()
                            .await()
                        query.toObject(ForumCollection::class.java)?.let { data.add(it) }
                    }
                }

                dataForumUser.addAll(data)

            } catch (e: Exception) {
                Log.d("INI ERROR KENAPA", "getDataPortofolioUser: $e")
            }
        }
    }

    private fun getDataPortofolioUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val data = mutableListOf<DataPortofolio>()


                val querySnapshot =
                    firestore.collection("portofolio").get().await()

                querySnapshot.toObjects(DataPortofolio::class.java).forEach { portofolio->
                    if (portofolio.idUser == idCurrentUser){
                        data.add(portofolio)
                    }
                }

                dataPortolioUser.addAll(data)

            } catch (e: Exception) {
                Log.d("INI ERROR KENAPA", "getDataPortofolioUser: $e")
            }
        }
    }

    private fun getDataPortofolio() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val auth = Firebase.auth
                val idCurrentUser = auth.currentUser!!.uid
                val data = mutableListOf<DataPortofolio>()

                val querySnapshotUser =
                    firestore.collection("users/$idCurrentUser/likePortofolio")
                        .whereEqualTo("like", true).get().await()

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

            } catch (_: Exception) {

            }
        }

    }


}
