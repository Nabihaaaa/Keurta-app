package com.example.seccraft_app.screens.artikel

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.User.UserLikeArtikel
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.artikel.DataLikeArtikel
import com.example.seccraft_app.collection.artikel.DataViewArtikel
import com.example.seccraft_app.collection.portofolio.LikePortofolio
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.blur
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailArtikelScreen(navController: NavHostController, documentId: String) {

    var artikel by remember { mutableStateOf(DataArtikel()) }
    var timeAgo by remember { mutableStateOf("") }
    var likeData by remember { mutableStateOf(DataLikeArtikel()) }
    var viewDataCollection by remember { mutableStateOf(listOf<DataViewArtikel>()) }
    var viewCount by remember { mutableStateOf(0.toLong()) }

    JetFirestore(
        path = { collection("artikel/$documentId/view") },
        onRealtimeCollectionFetch = { values, exception ->
            viewDataCollection = values.getListOfObjects()
        },
    ) {

        viewCount = viewDataCollection.count().toLong()

    }

    if (artikel.time != null) {
        val timestamp = artikel.time as com.google.firebase.Timestamp

        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val currentTime = System.currentTimeMillis()

        val diffInMillis = currentTime - milliseconds
        val diffInSeconds = diffInMillis / 1000
        val diffInMinutes = diffInSeconds / 60
        val diffInHours = diffInMinutes / 60
        val diffInDays = diffInHours / 24
        val diffInWeeks = diffInDays / 7
        val diffInMonths = diffInDays / 30
        val diffInYears = diffInDays / 365

        timeAgo = when {
            diffInYears >= 1 -> "${diffInYears.toInt()} tahun lalu"
            diffInMonths >= 1 -> "${diffInMonths.toInt()} bulan lalu"
            diffInWeeks >= 1 -> "${diffInWeeks.toInt()} minggu lalu"
            diffInDays >= 1 -> "${diffInDays.toInt()} hari lalu"
            diffInHours >= 1 -> "${diffInHours.toInt()} jam lalu"
            diffInMinutes >= 1 -> "${diffInMinutes.toInt()} menit lalu"
            else -> "Baru saja"
        }
    }


    Log.d("isiId", "DetailArtikelScreen: $documentId")

    Accompanist().TopBar(bg)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        item {
            Surface(modifier = Modifier.fillMaxSize(), color = bg) {
                JetFirestore(
                    path = { document("artikel/$documentId") },
                    onRealtimeDocumentFetch = { values, ex ->
                        val id = values!!.getString("id").toString()
                        val image = values.getString("image").toString()
                        val title = values.getString("title").toString()
                        val deskripsi = values.getString("deskripsi").toString()
                        val pembuat = values.getString("pembuat").toString()
                        val view = values.getLong("view") ?: 0
                        val time = values.get("time")

                        artikel = artikel.copy(id, image, title, deskripsi, pembuat,view, time)
                    }
                ) {
                    if (artikel.id!=""){
                        ViewArtikel(artikel.id,viewCount)
                    }
                    Box {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(410.dp),
                            shape = RectangleShape
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(model = artikel.image),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                        }

                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 150.dp)
                        ) {

                            Card(
                                modifier = Modifier
                                    .padding()
                                    .size(44.dp)
                                    .clickable {
                                        navController.navigate(Screens.Artikel.route)
                                    },
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(blur)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_left),
                                    contentDescription = "",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxSize()
                                )

                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Card(
                                modifier = Modifier
                                    .padding()
                                    .size(44.dp)
                                    .clickable {
                                        navController.navigate(Screens.Artikel.route)
                                    },
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(blur)
                            ) {

                                val auth = Firebase.auth
                                val idCurrentUser = auth.currentUser!!.uid

                                JetFirestore(
                                    path = { document("artikel/$documentId/like/$idCurrentUser") },
                                    onRealtimeDocumentFetch = { value, exception ->

                                        val idUserLike = value!!.getString("idUser").toString()
                                        val like = value.getBoolean("like")

                                        likeData = if (like == null) likeData.copy(
                                            idUserLike,
                                            false
                                        ) else likeData.copy(idUserLike, like)
                                    }

                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.love),
                                        contentDescription = "",
                                        tint = if (likeData.like) Color.Red else Color.Black,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                if (likeData.like) {
                                                    LikeArtikel(documentId, false)
                                                } else {
                                                    LikeArtikel(documentId, true)
                                                }

                                            }
                                    )
                                }

                            }

                        }

                        Text(
                            text = artikel.title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_eye),
                                contentDescription = "",
                                tint = Color.White
                            )

                            Text(
                                text = artikel.view.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                modifier = Modifier.padding(start = 16.dp)
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.dot),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 22.dp)
                                    .size(6.dp),
                                tint = Color(0xD6FFFFFF)
                            )

                            Text(
                                text = timeAgo,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xD6FFFFFF),
                            )


                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            colors = CardDefaults.cardColors(bg),
                            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                        ) {
                            Column(Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
                                Text(
                                    text = artikel.deskripsi,
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }

                        }

                    }

                }

            }
        }
    }

}

fun ViewArtikel(id: String, viewCount : Long) {
    val auth = Firebase.auth
    val db = Firebase.firestore
    val idCurrentUser = auth.currentUser!!.uid

    val data = DataViewArtikel(idCurrentUser)

    db.collection("artikel/$id/view/").document(idCurrentUser).set(data)
    db.document("artikel/$id").update("view", viewCount)
}

fun LikeArtikel(id: String, like: Boolean) {
    val auth = Firebase.auth
    val db = Firebase.firestore
    val idCurrentUser = auth.currentUser!!.uid

    val data = LikePortofolio(idUser = idCurrentUser, like = like)

    val dataUserPortofolio = UserLikeArtikel(idArtikel = id, like = like)


    db.collection("artikel/$id/like/").document(idCurrentUser).set(data)
    db.collection("users/$idCurrentUser/likeArtikel/").document(id).set(dataUserPortofolio)

}