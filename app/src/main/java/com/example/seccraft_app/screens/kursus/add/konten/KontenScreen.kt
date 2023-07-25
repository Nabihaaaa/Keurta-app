package com.example.seccraft_app.screens.kursus.add

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
    val listKonten = kontenKursusModel.listKonten


    var showDialog by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }
    var showDialogDeleteKonten by remember { mutableStateOf(false) }



    if (showDialog) {
        Alert(kontenKursusModel) {
            showDialog = it
        }
    }

    if (showDialogDelete) {
        AlertDelete(kontenKursusModel, navController) {
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
            LazyColumn {
                item {
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

                            if (!publikasi) {


                                //halaman utama
                                Text(
                                    text = "Halaman Utama Kursus",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                if (dataHalamanUtama.pembuat != "") {
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

                                //konten

                                Text(
                                    text = "Konten",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                if (listKonten.size != 0) {
                                    listKonten.forEachIndexed { idx, konten ->
                                        if (showDialogDeleteKonten) {
                                            AlertDeleteKonten(
                                                kontenKursusModel = kontenKursusModel,
                                                idKonten = konten.idKonten
                                            ) {
                                                showDialogDeleteKonten = it
                                            }
                                        }

                                        OutlinedCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 12.dp)
                                                .clickable {
                                                    navController.navigate("edit_konten_screen/$idKursus/${konten.idKonten}")
                                                },
                                            shape = RoundedCornerShape(15.dp),
                                            border = BorderStroke(1.dp, secondary),
                                            colors = CardDefaults.cardColors(Color.Transparent)
                                        ) {
                                            Box {
                                                Text(
                                                    text = konten.title,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    modifier = Modifier
                                                        .padding(vertical = 24.dp)
                                                        .fillMaxWidth(),
                                                    color = secondary,
                                                    textAlign = TextAlign.Center
                                                )

                                                IconButton(
                                                    onClick = {
                                                        showDialogDeleteKonten = true
                                                    },
                                                    modifier = Modifier.align(Alignment.TopEnd)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.close),
                                                        contentDescription = "",
                                                        modifier = Modifier.size(24.dp),
                                                        tint = Color.Red
                                                    )
                                                }


                                            }

                                        }

                                        if (idx == listKonten.size - 1) {
                                            val page = listKonten.size
                                            OutlinedButton(
                                                onClick = {
                                                    navController.navigate("add_konten_screen/$idKursus/$page")
                                                },
                                                contentPadding = PaddingValues(0.dp),
                                                border = BorderStroke(1.dp, Color.Blue),
                                                shape = RoundedCornerShape(15.dp),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 24.dp)
                                            ) {

                                                Text(
                                                    text = "+add",
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    modifier = Modifier.padding(vertical = 24.dp),
                                                    color = Color.Blue
                                                )


                                            }
                                        }
                                    }
                                } else {
                                    val page = 0
                                    OutlinedButton(
                                        onClick = {
                                            navController.navigate("add_konten_screen/$idKursus/$page")
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        border = BorderStroke(1.dp, Color.Blue),
                                        shape = RoundedCornerShape(15.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 24.dp)
                                    ) {
                                        Text(
                                            text = "+add",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(vertical = 24.dp),
                                            color = Color.Blue
                                        )
                                    }
                                }
                            }
                            //preview

                            Text(
                                text = "Preview",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            if (dataHalamanUtama.pembuat != "") {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 24.dp)
                                        .clickable {
                                            navController.navigate("kursus_detail_screen/$idKursus")
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

                            if (publikasi) {
                                Text(
                                    text = stringResource(id = R.string.total_penghasilan),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.jml_pembeli),
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = dataHalamanUtama.pengikut.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.paket_kursus),
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = formatHarga(dataHalamanUtama.harga),
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                }
                                Divider(
                                    thickness = 1.dp, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                )
                                val total = dataHalamanUtama.harga * dataHalamanUtama.pengikut

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 42.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.total_penghasilan),
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "Rp. ${formatHarga(total)}",
                                        style = MaterialTheme.typography.headlineLarge,
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
                Text(
                    text = "Ya",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
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
                Text(
                    text = "Batal",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
        },
        containerColor = bg,
        textContentColor = Color.Black
    )
}

@Composable
fun AlertDelete(
    kontenKursusModel: DataKontenModel,
    navController: NavHostController,
    showDialog: (Boolean) -> Unit
) {
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
                        navController.navigate(BottomBarScreen.Kursus.route)
                    }

                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(secondary)
            ) {
                Text(
                    text = "Ya",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
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
                Text(
                    text = "Batal",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
        },
        containerColor = bg,
        textContentColor = Color.Black
    )

}

@Composable
fun AlertDeleteKonten(
    kontenKursusModel: DataKontenModel,
    idKonten: String,
    showDialog: (Boolean) -> Unit
) {
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
                text = stringResource(id = R.string.yakin_delete_konten),
                style = MaterialTheme.typography.labelMedium
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        kontenKursusModel.deleteKonten(idKonten)
                    }
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(secondary)
            ) {
                Text(
                    text = "Ya",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
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
                Text(
                    text = "Batal",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
        },
        containerColor = bg,
        textContentColor = Color.Black
    )

}

