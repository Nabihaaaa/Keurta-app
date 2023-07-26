package com.example.seccraft_app.screens.auth.registerPaguyuban

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.R
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrasiPaguyubanScreen(
    navController: NavHostController,
    registrasiPaguyubanModel: RegistrasiPaguyubanModel = viewModel()
) {
    Accompanist().TopBar(color = primary)
    var nama by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var nomor by remember {
        mutableStateOf("")
    }
    var alamat by remember {
        mutableStateOf("")
    }
    var deskripsi by remember {
        mutableStateOf("")
    }
    var image by remember {
        mutableStateOf<Uri?>(null)
    }
    var suratIzin by remember {
        mutableStateOf<Uri?>(null)
    }
    var buktiLain by remember {
        mutableStateOf<Uri?>(null)
    }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcherIzin =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            suratIzin = uri
        }


    val launcherbukti =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            buktiLain = uri
        }
    val launcherImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            image = uri
        }


    if (showDialog) {
        if (nama != "" && email != "" && password != ""
            && nomor != "" && alamat != "" && deskripsi != ""
            && suratIzin != null && password.length >= 6 && isEmailValid(email) && image != null
        ) {
            UploadAlert(
                nama,
                email,
                password,
                nomor,
                alamat,
                deskripsi,
                suratIzin!!,
                buktiLain,
                navController,
                registrasiPaguyubanModel,
                context,
                image!!
            ) {
                showDialog = it
            }
        } else {
            Toast.makeText(context, R.string.data_blm_lengkap, Toast.LENGTH_LONG).show()
        }
    }


    Surface(modifier = Modifier.fillMaxSize(), color = primary) {
        LazyColumn {
            item() {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(120.dp)
                            .height(80.dp)
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                        shadowElevation = 30.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 28.dp)
                                .padding(top = 32.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.daftar),
                                fontFamily = PoppinsFamily,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = green_32
                            )
                            Text(
                                text = stringResource(id = R.string.daftar_desc_pag),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = gray_88,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 48.dp)
                            )
                            //nama
                            TextFieldRegPag(title = stringResource(id = R.string.nama_pag)) {
                                nama = it
                            }
                            //email
                            TextFieldRegPag(title = stringResource(id = R.string.email_pag), type = "email") {
                                email = it
                            }
                            //password
                            TextFieldRegPag(
                                title = stringResource(id = R.string.password_pag),
                                keyboardType = KeyboardType.Password,
                                type = "password"
                            ) {
                                password = it
                            }
                            //nomor
                            TextFieldRegPag(
                                title = stringResource(id = R.string.nomor_pag),
                                keyboardType = KeyboardType.Number
                            ) {
                                nomor = it
                            }
                            //alamat
                            TextFieldRegPag(
                                title = stringResource(id = R.string.alamat_pag),
                                heightMod = Modifier.height(82.dp),
                                singleLine = false
                            ) {
                                alamat = it
                            }
                            //deskripsi
                            TextFieldRegPag(
                                title = stringResource(id = R.string.deskripsi_pag),
                                heightMod = Modifier.height(82.dp),
                                singleLine = false
                            ) {
                                deskripsi = it
                            }
                            //Photo Profile
                            Text(
                                text = stringResource(id = R.string.photo_pag),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                color = gray_88,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            if (image != null) {
                                Card(modifier = Modifier
                                    .size(160.dp)
                                    .padding(vertical = 8.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = image),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }
                            OutlinedButton(
                                onClick = { launcherImage.launch("image/*") },
                                border = BorderStroke(1.dp, gray_88),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                shape = RoundedCornerShape(size = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon__data_transfer),
                                        contentDescription = "",
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.upl_file),
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(600),
                                        color = gray_88,
                                    )

                                }
                            }
                            //surat izin
                            Text(
                                text = stringResource(id = R.string.surat_izin),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                color = gray_88,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            if (suratIzin != null) {
                                Card(modifier = Modifier
                                    .size(160.dp)
                                    .padding(vertical = 8.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = suratIzin),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }
                            OutlinedButton(
                                onClick = { launcherIzin.launch("image/*") },
                                border = BorderStroke(1.dp, gray_88),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                shape = RoundedCornerShape(size = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon__data_transfer),
                                        contentDescription = "",
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.upl_file),
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(600),
                                        color = gray_88,
                                    )

                                }
                            }
                            //bukti tambahan
                            var fileBuktiName by remember {
                                mutableStateOf("")
                            }
                            if (buktiLain != null) {
                                buktiLain?.let {
                                    context.contentResolver.query(it, null, null, null, null)
                                }?.use { cursor ->
                                    val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                    cursor.moveToFirst()
                                    fileBuktiName = cursor.getString(name)
                                }
                            }

                            Log.d("ISI FILE NAME", "RegistrasiPaguyuban: $fileBuktiName")

                            Text(
                                text = stringResource(id = R.string.bukti_tambahan),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                color = gray_88,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            OutlinedButton(
                                onClick = { launcherbukti.launch("application/zip") },
                                border = BorderStroke(1.dp, gray_88),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                shape = RoundedCornerShape(size = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                                ) {
                                    Icon(
                                        painter = if (buktiLain != null) painterResource(id = R.drawable.icn_zip) else painterResource(
                                            id = R.drawable.icon__data_transfer
                                        ),
                                        contentDescription = "",
                                        modifier = Modifier.padding(end = 12.dp),
                                        tint = if (buktiLain != null) Color.Black else gray_88
                                    )
                                    Text(
                                        text = if (buktiLain != null) fileBuktiName else stringResource(
                                            id = R.string.upl_file
                                        ),
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(600),
                                        color = if (buktiLain != null) secondary else gray_88,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = false
                                    )


                                }
                            }
                            //syarat
                            Text(
                                text = stringResource(id = R.string.syarat),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = gray_88,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 32.dp, bottom = 16.dp)
                                    .padding(horizontal = 24.dp)
                            )
                            //Btn Daftar
                            Button(
                                onClick = {
                                    showDialog = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(secondary)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.daftar),
                                    fontFamily = PoppinsFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                )
                            }
                            //masuk
                            Row(
                                modifier = Modifier
                                    .padding(top = 48.dp, bottom = 100.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.sudah_akun),
                                    fontFamily = PoppinsFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = gray_88
                                )
                                ClickableText(
                                    text = AnnotatedString(stringResource(id = R.string.masuk)),
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = TextStyle(
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = gray_88
                                    ),
                                    onClick = {
                                        navController.navigate(Screens.Login.route)
                                    }
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
fun UploadAlert(
    nama: String,
    email: String,
    password: String,
    nomor: String,
    alamat: String,
    deskripsi: String,
    suratIzin: Uri,
    buktiLain: Uri?,
    navController: NavHostController,
    registrasiPaguyubanModel: RegistrasiPaguyubanModel,
    context : Context,
    image : Uri,
    showDialog: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { showDialog(false) },
        title = {
            Text(
                text = stringResource(id = R.string.konfirmasi_registrasi),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.deskripsi_registrasi_paguyuban),
                style = MaterialTheme.typography.labelMedium
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        registrasiPaguyubanModel.upload(
                            nama,
                            email,
                            password,
                            nomor,
                            alamat,
                            deskripsi,
                            suratIzin,
                            buktiLain,
                            navController,
                            context,
                            image
                        )

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
fun isEmailValid(email: String): Boolean {
    // Pattern untuk validasi alamat email
    val emailPattern = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return email.matches(emailPattern)
}




