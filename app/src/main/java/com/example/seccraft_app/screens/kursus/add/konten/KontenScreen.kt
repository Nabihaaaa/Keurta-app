package com.example.seccraft_app.screens.kursus.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.screens.kursus.add.konten.DataKontenModel
import com.example.seccraft_app.screens.kursus.add.konten.KontenVIewModelFactory
import com.example.seccraft_app.screens.kursus.getPembuat
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.screens.util.formatHarga
import com.example.seccraft_app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontenScreen(
    navController: NavHostController,
    idKursus: String?,
    kontenKursusModel: DataKontenModel =
        viewModel(factory = KontenVIewModelFactory(idKursus!!))
) {
    val coroutineScope = rememberCoroutineScope()
    val dataHalamanUtama = kontenKursusModel.halamanUtama.value
    val publikasi = dataHalamanUtama.publikasi

    var showDialog by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }

    if (showDialog) {
        Alert(kontenKursusModel) {
            showDialog = it
        }
    }

    if (showDialogDelete) {
        AlertDelete(kontenKursusModel,navController) {
            showDialogDelete = it
        }
    }

    Accompanist().TopBar(color = primary)
    Scaffold(
        bottomBar = {
            BottomBarAddKontenScreen(publikasi) {
                showDialog = it
            }
        }
    ) {
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
                            text = stringResource(id = R.string.kursus),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp),
                            textAlign = TextAlign.Center
                        )

                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 32.dp)
                ) {

                    //halaman utama
                    Text(
                        text = "Halaman Utama Kursus",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    if (dataHalamanUtama.pembuat!="") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                                .clickable {
                                    navController.navigate("edit_halaman_utama_screen/$idKursus")

                                }
                        ) {
                            Card(
                                modifier = Modifier.size(116.dp),
                                shape = RoundedCornerShape(15.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = dataHalamanUtama.image),
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

                                Text(
                                    text = dataHalamanUtama.level,
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = dataHalamanUtama.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                var pembuat by remember {
                                    mutableStateOf("")
                                }
                                LaunchedEffect(Unit) {
                                    coroutineScope.launch {
                                        // Panggil fungsi suspend di sini
                                        pembuat = getPembuat(dataHalamanUtama.pembuat)
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
                                    text = "Rp. " + formatHarga(dataHalamanUtama.harga),
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .fillMaxWidth()
                                )


                            }

                        }
                    }

                    Text(
                        text = "Konten",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, secondary),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "+add",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 24.dp),
                            color = secondary
                        )
                    }

                    Text(
                        text = "Preview",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    if (dataHalamanUtama.pembuat!="") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                                .clickable {

                                }
                        ) {
                            Card(
                                modifier = Modifier.size(116.dp),
                                shape = RoundedCornerShape(15.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = dataHalamanUtama.image),
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

                                Text(
                                    text = dataHalamanUtama.level,
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = dataHalamanUtama.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                var pembuat by remember {
                                    mutableStateOf("")
                                }
                                LaunchedEffect(Unit) {
                                    coroutineScope.launch {
                                        // Panggil fungsi suspend di sini
                                        pembuat = getPembuat(dataHalamanUtama.pembuat)
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
                                    text = "Rp. " + formatHarga(dataHalamanUtama.harga),
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .fillMaxWidth()
                                )


                            }

                        }
                    }

                    if (!publikasi) {
                        Button(
                            onClick = {
                                showDialogDelete = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                Color.Red
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_course),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun BottomBarAddKontenScreen(publikasi: Boolean, showDialog: (Boolean) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            if (publikasi) {
                Text(
                    text = stringResource(id = R.string.sudah_publikasi),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            } else {
                Button(
                    onClick = {
                        showDialog(true)
                    },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        secondary
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {

                    Text(
                        text = "Publikasikan",
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

@Composable
fun Alert(kontenKursusModel: DataKontenModel, showDialog: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { showDialog(false) },
        title = {
            Text(
                text = stringResource(id = R.string.konfirmasi_publikasi),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.yakin_publikasi),
                style = MaterialTheme.typography.labelMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        kontenKursusModel.publikasi()
                        showDialog(false)
                    }
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(secondary)
            ) {
                Text(text = "Ya", style = MaterialTheme.typography.displayMedium, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog(false)
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Batal", style = MaterialTheme.typography.displayMedium, color = Color.White)
            }
        },
        containerColor = bg,
        textContentColor = Color.Black
    )
}

@Composable
fun AlertDelete(kontenKursusModel: DataKontenModel,navController: NavHostController, showDialog: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { showDialog(false) },
        title = {
            Text(
                text = stringResource(id = R.string.konfirmasi_delete),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.yakin_delete),
                style = MaterialTheme.typography.labelMedium
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        kontenKursusModel.delete()
                    }
                    navController.navigate(BottomBarScreen.Kursus.route)
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(secondary)
            ) {
                Text(text = "Ya", style = MaterialTheme.typography.displayMedium, color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showDialog(false)
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Batal", style = MaterialTheme.typography.displayMedium, color = Color.White)
            }
        },
        containerColor = bg,
        textContentColor = Color.Black
    )

}

