package com.example.seccraft_app.screens.aktivitas

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.ui.theme.primary
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.forum.ForumCollection
import com.example.seccraft_app.screens.forum.CardItemForum
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.secondary
import com.jet.firestore.JetFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AktivitasScreen(
    navController: NavHostController,
    dataAktivitasModel: DataAktivitasModel = viewModel()
) {

    //suka
    val dataPortofolio = dataAktivitasModel.dataPortofolio
    val dataArtikel = dataAktivitasModel.dataArtikel
    //riwayat
    val dataPortolioUser = dataAktivitasModel.dataPortolioUser
    val dataForumUser = dataAktivitasModel.dataForumUser

    Accompanist().TopBar(color = primary)

    val pagerStateAktivitas = rememberPagerState()
    val pagerStateRiwayat = rememberPagerState()
    val pagerStateSuka = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()
    val coroutineScopeRiwayat = rememberCoroutineScope()
    val coroutineScopeSuka = rememberCoroutineScope()

    val buttonSelectedAktivitas = remember {
        derivedStateOf {
            pagerStateAktivitas.currentPage == 0
        }
    }

    val buttonSelectedRiwayat = remember {
        derivedStateOf {
            pagerStateRiwayat.currentPage == 0
        }
    }

    val buttonSelectedSuka = remember {
        derivedStateOf {
            pagerStateSuka.currentPage == 0
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(primary),
                shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.aktivitas),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, start = 20.dp, bottom = 64.dp)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 84.dp)
                        .height(86.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(modifier = Modifier
                                .padding(end = 18.dp)
                                .clickable {
                                    val prevPageIndex = pagerStateAktivitas.currentPage - 1
                                    coroutineScope.launch {
                                        pagerStateAktivitas.animateScrollToPage(
                                            prevPageIndex
                                        )
                                    }
                                }) {

                                Text(
                                    text = stringResource(id = R.string.riwayat),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = if (buttonSelectedAktivitas.value) Color.Black else Color(0x80252525),
                                )

                                if (buttonSelectedAktivitas.value){
                                    Divider(modifier = Modifier.width(64.dp), thickness = 1.dp, color = Color.Black)
                                }
                            }

                            Column( modifier = Modifier
                                .padding(end = 18.dp)
                                .clickable {
                                    val nextPageIndex = pagerStateAktivitas.currentPage + 1
                                    coroutineScope.launch {
                                        pagerStateAktivitas.animateScrollToPage(
                                            nextPageIndex
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.suka),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = if (!buttonSelectedAktivitas.value) Color.Black else Color(0x80252525),
                                    )

                                if (!buttonSelectedAktivitas.value){
                                    Divider(modifier = Modifier.width(40.dp), thickness = 1.dp, color = Color.Black)
                                }
                            }

                        }

                        HorizontalPager(pageCount = 2, state = pagerStateAktivitas) {
                            //Riwayat Button
                            if (it == 0) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Card(
                                        colors = if (buttonSelectedRiwayat.value) CardDefaults.cardColors(
                                            secondary
                                        ) else CardDefaults.cardColors(bg),
                                        modifier = Modifier.clickable {
                                            val prevPageIndex = pagerStateRiwayat.currentPage - 1
                                            coroutineScopeRiwayat.launch {
                                                pagerStateRiwayat.animateScrollToPage(
                                                    prevPageIndex
                                                )
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.portofolio),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (buttonSelectedRiwayat.value) Color.White else Color.Black,
                                            modifier = Modifier.padding(
                                                horizontal = 28.dp,
                                                vertical = 4.dp
                                            )
                                        )
                                    }

                                    Card(
                                        colors = if (!buttonSelectedRiwayat.value) CardDefaults.cardColors(
                                            secondary
                                        ) else CardDefaults.cardColors(bg),
                                        modifier = Modifier
                                            .padding(start = 18.dp)
                                            .clickable {
                                                val nextPageIndex =
                                                    pagerStateRiwayat.currentPage + 1

                                                coroutineScopeRiwayat.launch {
                                                    pagerStateRiwayat.animateScrollToPage(
                                                        nextPageIndex
                                                    )
                                                }
                                            }
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.forum),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (!buttonSelectedRiwayat.value) Color.White else Color.Black,
                                            modifier = Modifier.padding(
                                                horizontal = 28.dp,
                                                vertical = 4.dp
                                            )
                                        )

                                    }

                                }
                            }
                            //Suka Button
                            if (it == 1) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Card(
                                        colors = if (buttonSelectedSuka.value) CardDefaults.cardColors(
                                            secondary
                                        ) else CardDefaults.cardColors(bg),
                                        modifier = Modifier.clickable {
                                            val prevPageIndex = pagerStateSuka.currentPage - 1
                                            coroutineScopeSuka.launch {
                                                pagerStateSuka.animateScrollToPage(
                                                    prevPageIndex
                                                )
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.artikel),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (buttonSelectedSuka.value) Color.White else Color.Black,
                                            modifier = Modifier.padding(
                                                horizontal = 28.dp,
                                                vertical = 4.dp
                                            )
                                        )
                                    }

                                    Card(
                                        colors = if (!buttonSelectedSuka.value) CardDefaults.cardColors(
                                            secondary
                                        ) else CardDefaults.cardColors(bg),
                                        modifier = Modifier
                                            .padding(start = 18.dp)
                                            .clickable {
                                                val nextPageIndex = pagerStateSuka.currentPage + 1
                                                coroutineScopeSuka.launch {
                                                    pagerStateSuka.animateScrollToPage(
                                                        nextPageIndex
                                                    )
                                                }
                                            }
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.portofolio),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (!buttonSelectedSuka.value) Color.White else Color.Black,
                                            modifier = Modifier.padding(
                                                horizontal = 28.dp,
                                                vertical = 4.dp
                                            )
                                        )

                                    }

                                }
                            }
                        }

                    }
                }

                if (buttonSelectedAktivitas.value) {
                    HorizontalPager(pageCount = 2, state = pagerStateRiwayat) {

                        if (buttonSelectedRiwayat.value) {
                            CardItemPortofolioUser(dataPortolioUser, navController)
                        }
                        if (!buttonSelectedRiwayat.value) {

                            CardForumUser(dataForumUser, navController)

                        }

                    }

                }

                if (!buttonSelectedAktivitas.value) {
                    HorizontalPager(pageCount = 2, state = pagerStateSuka) {
                        if (buttonSelectedSuka.value) {
                            CardItemArtikel(dataArtikel, navController)

                        }
                        if (!buttonSelectedSuka.value) {
                            CardItemPortofolio(dataPortofolio, navController)
                        }

                    }
                }

            }

        }


    }

}

@Composable
fun CardForumUser(
    dataForumUser: SnapshotStateList<ForumCollection>,
    navController: NavHostController
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 18.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        items(dataForumUser) { forum ->
            CardItemForum(forum, navController)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemPortofolioUser(
    dataPortolioUser: SnapshotStateList<DataPortofolio>,
    navController: NavHostController
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 18.dp), modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Transparent
            )
    ) {
        items(dataPortolioUser) { portofolio ->

            var userPortofolio by remember { mutableStateOf(DataUser()) }

            JetFirestore(
                path = { document("users/${portofolio.idUser}") },
                onSingleTimeDocumentFetch = { value, exception ->
                    userPortofolio = userPortofolio.copy(
                        image = value!!.getString("image").toString(),
                        name = value.getString("name").toString()
                    )

                }
            ) {

            }
            Card(
                modifier = Modifier
                    .height(130.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("portofolio_detail_screen/${portofolio.id}") },
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Row {

                    Card(modifier = Modifier.size(130.dp), shape = RoundedCornerShape(7.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(model = portofolio.image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text(
                            text = portofolio.judul,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )

                        Text(
                            text = portofolio.deskripsi,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Card(modifier = Modifier.size(24.dp), shape = CircleShape) {
                                if (userPortofolio.image != "") {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = userPortofolio.image),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.user_profile),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Text(
                                text = userPortofolio.name,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(id = R.drawable.love),
                                contentDescription = "",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = portofolio.like.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )


                        }

                    }

                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemArtikel(artikel: SnapshotStateList<DataArtikel>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentPadding = PaddingValues(top = 18.dp),
        state = LazyListState()
    ) {

        itemsIndexed(artikel) { idx, dataArtikel ->

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
                    .padding(top = 16.dp),
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
                        modifier = Modifier.size(142.dp)
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

                            Text(
                                text = "By ${dataArtikel.pembuat}",
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


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemPortofolio(
    dataPortofolio: SnapshotStateList<DataPortofolio>,
    navController: NavHostController
) {

    LazyColumn(
        contentPadding = PaddingValues(top = 18.dp), modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Transparent
            )
    ) {
        items(dataPortofolio) { portofolio ->

            var userPortofolio by remember { mutableStateOf(DataUser()) }

            JetFirestore(
                path = { document("users/${portofolio.idUser}") },
                onSingleTimeDocumentFetch = { value, exception ->
                    userPortofolio = userPortofolio.copy(
                        image = value!!.getString("image").toString(),
                        name = value.getString("name").toString()
                    )

                }
            ) {

            }

            Card(
                modifier = Modifier
                    .height(130.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("portofolio_detail_screen/${portofolio.id}") },
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Row {

                    Card(modifier = Modifier.size(130.dp), shape = RoundedCornerShape(7.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(model = portofolio.image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text(
                            text = portofolio.judul,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )

                        Text(
                            text = portofolio.deskripsi,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Card(modifier = Modifier.size(24.dp), shape = CircleShape) {
                                if (userPortofolio.image != "") {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = userPortofolio.image),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.user_profile),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Text(
                                text = userPortofolio.name,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(id = R.drawable.love),
                                contentDescription = "",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = portofolio.like.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )


                        }

                    }

                }
            }

        }
    }

}

