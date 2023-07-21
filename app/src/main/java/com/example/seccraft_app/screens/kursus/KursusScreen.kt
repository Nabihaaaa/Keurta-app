package com.example.seccraft_app.screens.kursus

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.screens.util.formatHarga
import com.example.seccraft_app.ui.theme.*
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun KursusScreen(dataKursusModel: DataKursusModel = viewModel(), navController: NavHostController) {
    Accompanist().TopBar(color = primary)

    val dataKursus = dataKursusModel.dataKursus
    val dataUserKursus = dataKursusModel.dataUserKursus

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val buttonSelected = remember {
        derivedStateOf {
            pagerState.currentPage == 0
        }
    }

    var search by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Box {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(primary),
                shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.kursus),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 20.dp, bottom = 36.dp, top = 24.dp)
                )
            }

            Column {

                TextField(
                    value = search,
                    placeholder = {
                        Text(
                            stringResource(id = R.string.cari) + " " + stringResource(
                                id = R.string.kursus
                            )
                        )
                    },
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 72.dp)
                        .padding(horizontal = 32.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                    textStyle = MaterialTheme.typography.labelMedium,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        placeholderColor = icon_faded,
                        cursorColor = Color.Black,
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )

                )

                LazyColumn(contentPadding = PaddingValues(top = 16.dp)) {
                    item {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 20.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Divider(
                                modifier = Modifier
                                    .height(26.dp)
                                    .width(2.dp), color = Color(0xFF9A9999), thickness = 1.dp
                            )
                            Column(
                                modifier = Modifier.padding(end = 180.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.kursus),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = if (buttonSelected.value) Color.Black else Color(
                                        0xFF9A9999
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .clickable {
                                            val prevPageIndex = pagerState.currentPage - 1
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(
                                                    prevPageIndex
                                                )
                                            }
                                        },
                                )
                                if (buttonSelected.value) {
                                    Divider(
                                        modifier = Modifier
                                            .height(2.dp)
                                            .width(110.dp), color = Color.Black, thickness = 1.dp
                                    )
                                }

                            }
                            Column(
                                modifier = Modifier.padding(start = 190.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.kursus) + " saya",
                                    style = MaterialTheme.typography.displayLarge,
                                    color = if (!buttonSelected.value) Color.Black else Color(
                                        0xFF9A9999
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .clickable {
                                            val nextPageIndex = pagerState.currentPage + 1
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(
                                                    nextPageIndex
                                                )
                                            }
                                        }
                                )
                                if (!buttonSelected.value) {
                                    Divider(
                                        modifier = Modifier
                                            .height(2.dp)
                                            .width(110.dp), color = Color.Black, thickness = 1.dp
                                    )
                                }

                            }
                        }

                        HorizontalPager(pageCount = 2, state = pagerState) { pageIndex ->
                            //item Pola
                            if (pageIndex == 0) {
                                LazyItemKursus(dataKursus, search, true, navController)
                            }
                            if (pageIndex == 1) {
                                LazyItemKursus(dataUserKursus, search, false, navController)
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
fun LazyItemKursus(
    dataKursus: SnapshotStateList<DataKursus>,
    search: String,
    harga: Boolean,
    navController: NavHostController
) {
    var filteredList by remember {
        mutableStateOf(listOf<DataKursus>())
    }
    if (search != "") {
        filteredList = dataKursus.filter {
            it.title.contains(
                search,
                ignoreCase = true
            )
        }
    }

    val item = if (search != "") filteredList else dataKursus
    Column {
        item.forEachIndexed { idx, kursus ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .clickable {
                        if (harga) {
                            navController.navigate("kursus_detail_screen/${kursus.id}")
                        }else{
                            navController.navigate("konten_kursus_screen/${kursus.id}")
                        }
                    }
            ) {
                Card(
                    modifier = Modifier.size(116.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = kursus.image),
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

                    Text(text = kursus.level, style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = kursus.title,
                        style = MaterialTheme.typography.titleMedium,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "By ${kursus.pembuat}",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                    if (harga) {
                        Text(
                            text = "Rp. " + formatHarga(kursus.harga),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        )
                    }


                }

            }
            if (idx != dataKursus.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp), thickness = 1.dp, color = Color(0x4D000000)
                )
            }

        }
    }


}




