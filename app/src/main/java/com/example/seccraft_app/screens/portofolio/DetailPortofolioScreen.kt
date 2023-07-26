package com.example.seccraft_app.screens.portofolio

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.R
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.collection.user.DataUser
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.collection.portofolio.LikePortofolio
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.primary
import com.example.seccraft_app.ui.theme.secondary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPortofolioScreen(navcontroller: NavHostController, documentId: String) {

    Accompanist().TopBar(color = primary)

    var dataPortofolio by remember { mutableStateOf(DataPortofolio()) }

    var likeCount by remember { mutableStateOf(0) }
    var likeDataCollection by remember { mutableStateOf(listOf<LikePortofolio>()) }
    var likeData by remember { mutableStateOf(LikePortofolio()) }
    var user by remember { mutableStateOf(DataUser()) }


    JetFirestore(
        path = { document("portofolio/$documentId") },
        onRealtimeDocumentFetch = { value, ex ->
            val id = value!!.getString("id").toString()
            val idUser = value.getString("idUser").toString()
            val image = value.getString("image").toString()
            val judul = value.getString("judul").toString()
            val a = value.get("kategori")
            val deskripsi = value.getString("deskripsi").toString()
            val idKursus = value.getString("idKursus").toString()

            dataPortofolio = dataPortofolio.copy(
                id = id,
                idUser = idUser,
                image = image,
                judul = judul,
                kategori = a as List<Any?>?,
                deskripsi = deskripsi,
                idKursus = idKursus
            )

        }

    ) {

        if (dataPortofolio.kategori != null) {
            Log.d("Isi List Kategori", "DetailPortofolioScreen: ${dataPortofolio.kategori}")
        }


        Surface(modifier = Modifier.fillMaxSize(), color = bg) {
            Column {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        primary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 28.dp, bottom = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                navcontroller.navigate(Screens.Portofolio.route)
                            }
                        )

                        Text(
                            text = stringResource(id = R.string.portofolio),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 26.dp)
                        )

                    }

                }
                LazyColumn {
                    item {
                        JetFirestore(
                            path = { collection("portofolio/$documentId/like") },
                            onRealtimeCollectionFetch = { values, exception ->
                                likeDataCollection = values.getListOfObjects()
                            },
                        ) {
                            likeCount = likeDataCollection.count {
                                it.like
                            }
                            Log.d("likeCount", "DetailPortofolioScreen: $likeCount")
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp), shape = RectangleShape
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = dataPortofolio.image),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                        ) {
                            Row(modifier = Modifier.padding(top = 16.dp)) {
                                Text(
                                    text = dataPortofolio.judul,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 20.sp,
                                    modifier = Modifier.fillMaxWidth(0.7f)
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                val auth = Firebase.auth
                                val idCurrentUser = auth.currentUser!!.uid

                                JetFirestore(
                                    path = { document("portofolio/$documentId/like/$idCurrentUser") },
                                    onRealtimeDocumentFetch = { value, exception ->

                                        val idUserLike = value!!.getString("idUser").toString()
                                        val like = value.getBoolean("like")

                                        likeData = if (like == null) likeData.copy(
                                            idUserLike,
                                            false
                                        ) else likeData.copy(idUserLike, like)
                                    }

                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.love),
                                            contentDescription = "",
                                            tint = if (likeData.like) Color.Red else Color.Black,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .padding(bottom = 2.dp)
                                                .clickable {
                                                    if (likeData.like) {
                                                        LikePto(dataPortofolio.id, false, likeCount)
                                                    } else {
                                                        LikePto(dataPortofolio.id, true, likeCount)
                                                    }

                                                }
                                        )
                                        Text(
                                            text = likeCount.toString(),
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    }
                                }

                            }

                            Text(
                                text = dataPortofolio.deskripsi,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp)
                            )

                            if (dataPortofolio.idUser != "") {
                                JetFirestore(
                                    path = { document("users/${dataPortofolio.idUser}") },
                                    onRealtimeDocumentFetch = { value, exception ->

                                        val email = value!!.getString("email").toString()
                                        val name = value.getString("name").toString()
                                        val number = value.getString("number").toString()
                                        val image = value.getString("image").toString()

                                        user = user.copy(image, name, email, number)
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Card(modifier = Modifier.size(42.dp), shape = CircleShape) {
                                            Image(
                                                painter = rememberAsyncImagePainter(model = user.image),
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.FillBounds
                                            )
                                        }

                                        Text(
                                            text = user.name,
                                            style = MaterialTheme.typography.displayMedium,
                                            modifier = Modifier.padding(start = 14.dp)
                                        )
                                    }
                                }
                            }


                            if (dataPortofolio.kategori != null && dataPortofolio.kategori!!.isNotEmpty()) {
                                Row(modifier = Modifier.fillMaxWidth()) {

                                    dataPortofolio.kategori!!.forEachIndexed { idx, kategori ->
                                        if (kategori.toString() != "") {
                                            Card(
                                                shape = RoundedCornerShape(12.dp),
                                                modifier = Modifier.padding(
                                                    top = 24.dp,
                                                    bottom = 50.dp,
                                                    start = if (idx == 0) 0.dp else 12.dp
                                                ),
                                                colors = CardDefaults.cardColors(
                                                    secondary
                                                )
                                            ) {
                                                Text(
                                                    text = kategori.toString(),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(
                                                        vertical = 8.dp,
                                                        horizontal = 24.dp
                                                    )
                                                )
                                            }
                                        }
                                    }

                                }

                            }


                            Text(
                                text = stringResource(id = R.string.informasi_cource),
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(bottom = 14.dp, top = 24.dp)
                            )

                            if (dataPortofolio.idKursus != "") {
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp).clickable {
                                            navcontroller.navigate("kursus_detail_screen/${dataPortofolio.idKursus}")
                                    },
                                    colors = CardDefaults.cardColors(Color(0xFFA6D1B2)),
                                    shape = RoundedCornerShape(size = 14.dp)
                                ) {

                                    var dataKursus by remember {
                                        mutableStateOf(DataKursus())
                                    }

                                    JetFirestore(
                                        path = { document("kursus/${dataPortofolio.idKursus}") },
                                        onSingleTimeDocumentFetch = { values, ex ->
                                            val image = values!!.getString("image").toString()
                                            val title = values.getString("title").toString()
                                            dataKursus =
                                                dataKursus.copy(image = image, title = title)
                                        }
                                    ) {


                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .size(74.dp)
                                            ) {
                                                Image(
                                                    painter = rememberAsyncImagePainter(model = dataKursus.image),
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.FillBounds
                                                )
                                            }


                                            Text(
                                                text = dataKursus.title,
                                                style = MaterialTheme.typography.displayMedium,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(start = 24.dp)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Icon(
                                                painter = painterResource(id = R.drawable.arrow_right),
                                                contentDescription = "",
                                                Modifier.padding(end = 16.dp).size(24.dp)
                                            )


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
}




