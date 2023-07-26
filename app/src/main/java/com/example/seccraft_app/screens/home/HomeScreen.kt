package com.example.seccraft_app.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.collection.portofolio.LikePortofolio
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.user.DataUser
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.screens.kursus.getPembuat
import com.example.seccraft_app.screens.portofolio.LikePto
import com.example.seccraft_app.screens.util.formatHarga
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, dataHomeModel: DataHomeModel = viewModel()) {
    Accompanist().TopBar(color = primary)

    val portofolio = dataHomeModel.portofolio
    val user = dataHomeModel.user.value
    val artikel = dataHomeModel.artikel
    val kursus = dataHomeModel.kursus

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(), color = bg
    ) {

        LazyColumn {
            item {

                Column {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(primary),
                        shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
                    ) {
                        Column(modifier = Modifier.padding(start = 20.dp, top = 16.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.selamat) + " ${user.name}!",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                            )
                            Log.d("nama suka ga muncul", "HomeScreen: ${user.name}")
                            Text(
                                text = stringResource(id = R.string.semangat),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                        }

                    }

                    if (user.role == "user"){
                        KursusCardItem(kursus, navController)
                    }
                    PortofolioCardItem(portofolio, navController)
                    ArtikelCardItem(artikel, navController)
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtikelCardItem(artikel: MutableList<DataArtikel>, navController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.artikel),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    navController.navigate(Screens.Artikel.route)
                }
        )
    }

    artikel.forEachIndexed { idx, dataArtikel ->

        val timestamp = dataArtikel.time as com.google.firebase.Timestamp

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

        val timeAgo = when {
            diffInYears >= 1 -> "${diffInYears.toInt()} tahun lalu"
            diffInMonths >= 1 -> "${diffInMonths.toInt()} bulan lalu"
            diffInWeeks >= 1 -> "${diffInWeeks.toInt()} minggu lalu"
            diffInDays >= 1 -> "${diffInDays.toInt()} hari lalu"
            diffInHours >= 1 -> "${diffInHours.toInt()} jam lalu"
            diffInMinutes >= 1 -> "${diffInMinutes.toInt()} menit lalu"
            else -> "Baru saja"
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("artikel_detail_screen/${dataArtikel.id}")
                }
                .padding(top = 16.dp, start = 20.dp, end = 20.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            shape = RectangleShape,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(142.dp)
                    .padding(bottom = 24.dp)
            ) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.size(142.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = dataArtikel.image),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 6.dp, end = 14.dp)
                ) {
                    Text(
                        text = timeAgo,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = dataArtikel.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val coroutineScope = rememberCoroutineScope()
                        var pembuat by remember {
                            mutableStateOf("")
                        }
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                // Panggil fungsi suspend di sini
                                pembuat = getPembuat(dataArtikel.pembuat)
                            }
                        }

                        Text(
                            text = "By $pembuat",
                            style = MaterialTheme.typography.labelSmall,

                            )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(id = R.drawable.icon_eye),
                            contentDescription = "",
                            Modifier.size(22.dp)

                        )
                        Text(
                            text = dataArtikel.view.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 12.dp)
                        )

                    }

                }

            }

            if (idx != artikel.size - 1) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0x4D000000)
                )
            }


        }

    }

    Spacer(modifier = Modifier.height(32.dp))

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PortofolioCardItem(
    portofolio: MutableList<DataPortofolio>,
    navController: NavHostController,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.portofolio),
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate(Screens.Portofolio.route)
                    }
            )

        }
        LazyRow(
            contentPadding = PaddingValues(start = 4.dp),
            modifier = Modifier
                .padding(top = 16.dp)
                .wrapContentHeight()
        ) {
            items(portofolio) { data ->

                var userPortofolio by remember {
                    mutableStateOf(DataUser())
                }
                var dataLike by remember { mutableStateOf(listOf<LikePortofolio>()) }
                var likeCount by remember { mutableStateOf(0) }
                var likeStatus by remember { mutableStateOf(LikePortofolio()) }

                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .padding(start = 16.dp)
                        .combinedClickable(
                            onClick = {
                                navController.navigate("portofolio_detail_screen/${data.id}")
                            },
                        ),
                    shape = RoundedCornerShape(7.dp),
                    colors = CardDefaults.cardColors(
                        primary
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(124.dp),
                        shape = RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = data.image),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Row(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    ) {


                        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                            Text(
                                text = data.judul,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(bottom = 8.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            JetFirestore(
                                path = { document("users/${data.idUser}") },
                                onSingleTimeDocumentFetch = { value, exception ->
                                    val email = value!!.getString("email").toString()
                                    val name = value.getString("name").toString()
                                    val number = value.getString("number").toString()
                                    val image = value.getString("image").toString()

                                    userPortofolio = userPortofolio.copy(image, name, email, number)
                                }
                            ) {
                                Text(
                                    text = userPortofolio.name.substringBefore(" "),
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }


                        }
                        Spacer(modifier = Modifier.weight(1f))



                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            JetFirestore(
                                path = { collection("portofolio/${data.id}/like") },
                                onRealtimeCollectionFetch = { values, exception ->
                                    dataLike = values.getListOfObjects()
                                }
                            ) {

                                likeCount = dataLike.count {
                                    it.like
                                }

                                val auth = Firebase.auth
                                val idCurrentUser = auth.currentUser!!.uid

                                JetFirestore(
                                    path = { document("portofolio/${data.id}/like/$idCurrentUser") },
                                    onRealtimeDocumentFetch = { value, exception ->

                                        val idUserLike = value!!.getString("idUser").toString()
                                        val like = value.getBoolean("like")

                                        likeStatus = if (like == null) likeStatus.copy(
                                            idUserLike,
                                            false
                                        ) else likeStatus.copy(idUserLike, like)

                                    }
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.love),
                                        contentDescription = "",
                                        tint = if (likeStatus.like) Color.Red else Color.Black,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                if (likeStatus.like) {
                                                    LikePto(data.id, false, likeCount)
                                                } else {
                                                    LikePto(data.id, true, likeCount)
                                                }
                                            },
                                    )
                                    Text(
                                        text = likeCount.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(top = 8.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KursusCardItem(kursus: MutableList<DataKursus>, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.kursus),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    navController.navigate(BottomBarScreen.Kursus.route)
                }
        )
    }

    kursus.forEachIndexed { idx, dataKursus ->
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .clickable {
                        navController.navigate("kursus_detail_screen/${dataKursus.id}")
                    }

            ) {
                Card(
                    modifier = Modifier.size(116.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = dataKursus.image),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                ) {

                    Text(text = dataKursus.level, style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = dataKursus.title,
                        style = MaterialTheme.typography.titleMedium,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    val coroutineScope = rememberCoroutineScope()
                    var pembuat by remember {
                        mutableStateOf("")
                    }
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            // Panggil fungsi suspend di sini
                            pembuat = getPembuat(dataKursus.pembuat)
                        }
                    }
                    Text(
                        text = "By $pembuat",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = "Rp. " + formatHarga(dataKursus.harga),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )


                }

            }
            if (idx != kursus.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp), thickness = 1.dp, color = Color(0x4D000000)
                )
            }

        }
    }

}


