package com.example.seccraft_app.screens.kursus

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.User.DataUserKursus
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.collection.kursus.DataPengikutKursus
import com.example.seccraft_app.screens.util.formatHarga
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PembayaranScreen(navController: NavHostController, documentId: String) {
    var kursus by remember {
        mutableStateOf(DataKursus())
    }
    Log.d("isiDocs", "PembayaranScreen: $documentId")

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Panggil fungsi suspend di sini
            kursus = getKursusWithId(documentId)
        }
    }

    Scaffold(bottomBar = { BottomPembayaran(navController, kursus) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), color = bg
        ) {
            LazyColumn {
                item {
                    Column(modifier = Modifier.padding(bottom = 100.dp)) {
                        //Top Bar
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(primary),
                            shape = RectangleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    start = 20.dp,
                                    bottom = 28.dp
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

                        //CARD PAKET KURSUS

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                        ) {

                            Text(
                                text = stringResource(id = R.string.paket_pilih),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
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

                                    Text(
                                        text = kursus.level,
                                        style = MaterialTheme.typography.labelSmall
                                    )
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


                                }

                            }

                            Divider(
                                thickness = 1.dp, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            //PROMO

                            Text(
                                text = stringResource(id = R.string.promo),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TextField(
                                value = "",
                                placeholder = { Text(text = stringResource(id = R.string.gunakan_cd_prm)) },
                                onValueChange = {},
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_right_ver2),
                                        contentDescription = ""
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFA19B9B),
                                        shape = RoundedCornerShape(size = 10.dp)
                                    ),
                                textStyle = TextStyle(
                                    fontFamily = PoppinsFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
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

                            Divider(
                                thickness = 1.dp, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            //METODE PEMBAYARAN
                            Text(
                                text = stringResource(id = R.string.metode_pembayaran),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TextField(
                                value = "",
                                placeholder = { Text(text = stringResource(id = R.string.pilih_mtd)) },
                                onValueChange = {},
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_right_ver2),
                                        contentDescription = ""
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFA19B9B),
                                        shape = RoundedCornerShape(size = 10.dp)
                                    ),
                                textStyle = TextStyle(
                                    fontFamily = PoppinsFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
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

                            Divider(
                                thickness = 1.dp, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            val potonganHarga : Long = 0

                            //Ringkasan
                            Text(
                                text = stringResource(id = R.string.ringkasan),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)){
                                Text(
                                    text = stringResource(id = R.string.paket_kursus),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = formatHarga(kursus.harga),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)){
                                Text(
                                    text = stringResource(id = R.string.potongan_hrg),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = potonganHarga.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }

                            Divider(
                                thickness = 1.dp, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )

                            //total Pembayaran
                            val total = kursus.harga - potonganHarga

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = stringResource(id = R.string.total_pemby),
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

@Composable
fun BottomPembayaran(navController: NavHostController, kursus: DataKursus) {
    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {
        Box(contentAlignment = Alignment.Center) {
            Button(
                onClick = {

                          PembelianKursus(kursus,navController)


                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    secondary
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.bayar),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 32.dp)
                )

            }

        }
    }
}

fun PembelianKursus(kursus: DataKursus, navController: NavHostController) {

    val db = Firebase.firestore
    val currentUserId = Firebase.auth.currentUser!!.uid
    val timeNow = FieldValue.serverTimestamp()

    val data1 = DataPengikutKursus(currentUserId,timeNow)
    val data2 = DataUserKursus(kursus.id,timeNow)

    try {
        db.collection("kursus/${kursus.id}/usersKursus")
            .document(currentUserId)
            .set(data1)
            .addOnSuccessListener {
                db.collection("users/$currentUserId/kursus")
                    .document(kursus.id)
                    .set(data2)
                    .addOnSuccessListener{
                        navController.navigate(BottomBarScreen.Kursus.route)
                    }
            }

    }catch (e :Exception){
        Log.d("Error Kenapa", "PembelianKursus: $e ")
    }


}
