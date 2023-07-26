package com.example.seccraft_app.screens.admin.detail

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.R
import com.example.seccraft_app.screens.forum.ShowImage
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPaguyubanScreen(
    navController: NavController,
    id: String?,
    detailPaguyubanModel: DetailPaguyubanModel = viewModel(
        factory = DetailPaguyubanModelFactory(
            id!!
        )
    )
) {

    Accompanist().TopBar(color = primary)
    val context = LocalContext.current
    val paguyuban = detailPaguyubanModel.paguyuban.value
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ShowImage(image = paguyuban.suratIzin) {
            showDialog = it
        }
    }

    Scaffold(bottomBar = { DetailPaguyubanBottom() }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = bg
        ) {
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
                                navController.popBackStack()
                            }
                        )

                        Text(
                            text = stringResource(id = R.string.admin),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 26.dp)
                        )

                    }

                }

                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 24.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Card(modifier = Modifier.size(42.dp), shape = CircleShape) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = paguyuban.image),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                                Text(
                                    text = paguyuban.name,
                                    style = MaterialTheme.typography.displayLarge,
                                    modifier = Modifier.padding(start = 12.dp)
                                )

                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                colors = CardDefaults.cardColors(
                                    Color(0x0D000000)
                                )
                            ) {
                                //email
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 20.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.email),
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = paguyuban.email,
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(start = 20.dp)
                                        )
                                    }
                                    //number

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 20.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.contact),
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = paguyuban.nomor,
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(start = 20.dp)
                                        )
                                    }
                                    //address
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.address),
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = paguyuban.alamat,
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(start = 20.dp)
                                        )
                                    }


                                }

                            }
                            //Deskripsi
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                colors = CardDefaults.cardColors(
                                    Color(0x0D000000)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.deskripsi),
                                        style = MaterialTheme.typography.displayMedium,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                    Text(
                                        text = paguyuban.deskripsi,
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                }

                            }
                            //Surat izin
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                colors = CardDefaults.cardColors(
                                    Color(0x0D000000)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.surat_izinn),
                                        style = MaterialTheme.typography.displayMedium,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(240.dp)
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = paguyuban.suratIzin),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clickable {
                                                    showDialog = true
                                                },
                                            contentScale = ContentScale.FillBounds
                                        )

                                    }

                                }

                            }
                            //bukti Lainnya
                            if (paguyuban.buktiLain != "") {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp, bottom = 80.dp),
                                    colors = CardDefaults.cardColors(
                                        Color(0x0D000000)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 20.dp, vertical = 12.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.bukti_lain),
                                            style = MaterialTheme.typography.displayMedium,
                                            modifier = Modifier.padding(bottom = 12.dp)
                                        )


                                        Row(
                                            modifier = Modifier.clickable {
                                                if (detailPaguyubanModel.isWriteExternalStoragePermissionGranted(
                                                        context
                                                    )
                                                ) {
                                                    detailPaguyubanModel.getZip(
                                                        context,
                                                        paguyuban.buktiLain,
                                                        "bukti_lain.zip"
                                                    )
                                                } else {
                                                    detailPaguyubanModel.requestWriteExternalStoragePermission(
                                                        context
                                                    )
                                                }

                                            }

                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icn_zip),
                                                contentDescription = "",
                                                tint = secondary,
                                                modifier = Modifier.size(22.dp)
                                            )
                                            Text(
                                                text = "bukti_lain.zip",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = secondary,
                                                modifier = Modifier.padding(start = 12.dp)
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

@Composable
fun DetailPaguyubanBottom() {
    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {

                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    secondary
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(end = 20.dp)
                    .width(140.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.approve),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Button(
                onClick = {

                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Red
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.width(140.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }


    }
}



