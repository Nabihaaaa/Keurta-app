package com.example.seccraft_app.screens.kursus

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.KursusViewModelFactory
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun KontentKursusScreen(
    navController: NavHostController,
    idKursus: String?,
    kontenKursusModel: KontenKursusModel =
        viewModel(factory = KursusViewModelFactory(idKursus!!))
) {

    val dataKursus = kontenKursusModel.dataKursus.value
    val dataKonten = kontenKursusModel.kontenKursus
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        LazyColumn {
            item {
                HorizontalPager(pageCount = dataKonten.size, state = pagerState) {
                    val konten = dataKonten[pagerState.currentPage]
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(primary),
                            shape = RectangleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    top = 24.dp,
                                    start = 20.dp,
                                    bottom = 16.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_left),
                                    contentDescription = "",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            navController.navigate(BottomBarScreen.Kursus.route)
                                        }
                                )
                                Text(
                                    text = dataKursus.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 20.dp),
                                    textAlign = TextAlign.Center
                                )

                            }
                        }

                        //animasi or video

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(233.dp),
                            shape = RectangleShape,
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {

                        }
                        //deskripsi and judul
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 24.dp)
                        ) {

                            Text(
                                text = konten.title,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = konten.deskripsi,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }


                    }
                }
            }
        }

    }

}

