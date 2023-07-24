package com.example.seccraft_app.screens.kursus

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.DataAlatdanBahan
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.screens.util.LinkText
import com.example.seccraft_app.screens.util.LinkTextData
import com.example.seccraft_app.screens.util.formatHarga
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.primary
import com.example.seccraft_app.ui.theme.secondary
import com.example.seccraft_app.ui.theme.tertiary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKursusScreen(navController: NavHostController, documentId: String) {
    Accompanist().TopBar(color = primary)
    var kursus by remember {
        mutableStateOf(DataKursus())
    }
    var alat by remember {
        mutableStateOf(listOf<DataAlatdanBahan>())
    }
    var bahan by remember {
        mutableStateOf(listOf<DataAlatdanBahan>())
    }
    var userKursusCheck by remember {
        mutableStateOf(false)
    }


    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Panggil fungsi suspend di sini
            userKursusCheck = userKursusCheck(documentId)
            kursus = getKursusWithId(documentId)
            alat = getAlatDanBahan(documentId, "alat")
            bahan = getAlatDanBahan(documentId, "bahan")

        }
    }

    Scaffold(bottomBar = { BottomKursusDetail(kursus, navController, userKursusCheck) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), color = bg
        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(primary),
                    shape = RectangleShape
                ) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp, start = 20.dp, bottom = 28.dp),
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
                            text = kursus.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp),
                            textAlign = TextAlign.Center
                        )

                    }
                }

                LazyColumn {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            shape = RectangleShape,
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = kursus.image),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(
                            modifier = Modifier.padding(
                                top = 24.dp,
                                bottom = 54.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.deskripsi),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = kursus.deskripsi,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.alat_bahan),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.alat_list),
                                style = MaterialTheme.typography.labelMedium,
                            )
                            val context = LocalContext.current
                            alat.forEachIndexed { idx, alat ->
                                Log.d("isi alat", "DetailKursusScreen: ${alat.nama}")
                                LinkText(
                                    linkTextData = listOf(
                                        LinkTextData(
                                            text = "  ${idx + 1}. ${alat.nama} "
                                        ),
                                        LinkTextData(
                                            text = "(Link)",
                                            tag = "link_olshop",
                                            annotation = alat.link,
                                            onClick = { uri ->
                                                if (uri.item != "") {
                                                    val openURL = Intent(Intent.ACTION_VIEW)
                                                    openURL.data = Uri.parse(uri.item)
                                                    startActivity(context, openURL, null)
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "tidak memiliki link",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }

                                            }
                                        )
                                    ),

                                    )
                            }
                            Text(
                                text = stringResource(id = R.string.bahan_list),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            bahan.forEachIndexed { idx, bahan ->
                                LinkText(
                                    linkTextData = listOf(
                                        LinkTextData(
                                            text = "  ${idx + 1}. ${bahan.nama} "
                                        ),
                                        LinkTextData(
                                            text = "(Link)",
                                            tag = "link_olshop",
                                            annotation = bahan.link,
                                            onClick = {uri->
                                                if (uri.item != "") {
                                                    val openURL = Intent(Intent.ACTION_VIEW)
                                                    openURL.data = Uri.parse(uri.item)
                                                    startActivity(context, openURL, null)
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "tidak memiliki link",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        )
                                    ),

                                    )

                            }


                        }
                    }
                }


            }

        }
    }


}

@Composable
fun BottomKursusDetail(
    kursus: DataKursus,
    navController: NavHostController,
    userKursusCheck: Boolean
) {
    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {

        if (userKursusCheck) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(vertical = 16.dp),
            ) {
                Text(
                    text = "Anda sudah membeli kursus ini",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

        } else {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = kursus.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Rp. ${formatHarga(kursus.harga)}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = secondary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.navigate("pembayaran_kursus_screen/${kursus.id}")
                    },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        secondary
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.langganan),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )

                }

            }
        }


    }
}

