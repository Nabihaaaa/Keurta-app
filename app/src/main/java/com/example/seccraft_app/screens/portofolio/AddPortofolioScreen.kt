package com.example.seccraft_app.screens.portofolio

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.seccraft_app.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.collection.User.DataUserKursus
import com.example.seccraft_app.collection.kursus.DataAlatdanBahan
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.screens.kursus.getAlatDanBahan
import com.example.seccraft_app.screens.kursus.getDataKursusUser
import com.example.seccraft_app.screens.kursus.getKursusWithId
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import com.jet.firestore.getObject
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPortofolioScreen(navController: NavHostController) {
    Accompanist().TopBar(color = primary)
    Surface(modifier = Modifier.fillMaxSize(), color = bg) {

        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(primary),
                shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
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
                            navController.navigate(Screens.Portofolio.route)
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.portofolio),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 20.dp)
                    )

                }

            }
            LazyColumn() {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        var imageUri by remember { mutableStateOf<Uri?>(null) }

                        val launcher =
                            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                                imageUri = uri
                            }

                        Card(
                            modifier = Modifier
                                .clickable {
                                    launcher.launch("image/*")
                                }
                                .height(268.dp)
                                .fillMaxWidth()
                                .padding(top = 34.dp),
                            colors = CardDefaults.cardColors(Color(0xA3B9B9B9)),
                            shape = RoundedCornerShape(size = 14.dp)
                        ) {
                            if (imageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = imageUri),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.image),
                                        contentDescription = "",
                                        tint = Color(0x9C3D3D3D),
                                        modifier = Modifier.size(56.dp)
                                    )

                                    Text(
                                        text = stringResource(id = R.string.upl_image),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0x9C3D3D3D),
                                        modifier = Modifier.padding(top = 28.dp)
                                    )
                                }
                            }


                        }

                        var title by remember { mutableStateOf("") }
                        var deskripsi by remember { mutableStateOf("") }

                        TextField(
                            value = title,
                            placeholder = { Text(stringResource(id = R.string.judul)) },
                            onValueChange = {
                                title = it
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 35.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFA19B9B),
                                    shape = RoundedCornerShape(size = 6.dp)
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

                        var kursusUser by remember {
                            mutableStateOf(listOf<DataKursus>())
                        }

                        val coroutineScope = rememberCoroutineScope()
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                // Panggil fungsi suspend di sini
                                kursusUser = getDataKursusUser()
                            }
                        }

                        Log.d("isi Kursus", "AddPortofolioScreen: ${kursusUser.size}")

                        var expandedKursus by remember {
                            mutableStateOf(false)
                        }

                        var kursus by remember {
                            mutableStateOf("")
                        }
                        var kursusTitle by remember {
                            mutableStateOf("")
                        }

                        var textFieldSize by remember { mutableStateOf(Size.Zero) }

                        Column(modifier = Modifier.wrapContentHeight()) {
                            TextField(
                                value = kursusTitle,
                                placeholder = { Text(text = stringResource(id = R.string.kursus_diikuti), modifier = Modifier.clickable {
                                    expandedKursus = !expandedKursus
                                }) },
                                onValueChange = {
                                    kursusTitle = it
                                },
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    if (expandedKursus)
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_down),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .rotate(180f)
                                                .clickable {
                                                    expandedKursus = !expandedKursus
                                                }
                                        )
                                    else
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_down),
                                            contentDescription = "",
                                            modifier = Modifier.clickable {
                                                expandedKursus = !expandedKursus
                                            }
                                        )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp)
                                    .onGloballyPositioned { layoutCoordinates ->
                                        textFieldSize = layoutCoordinates.size.toSize()
                                    }
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFA19B9B),
                                        shape = RoundedCornerShape(size = 6.dp)
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

                            DropdownMenu(
                                expanded = expandedKursus,
                                onDismissRequest = { expandedKursus = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current){textFieldSize.width.toDp()})
                            ) {
                                kursusUser.forEach { label ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = label.title,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            kursus = label.id
                                            kursusTitle = label.title
                                            expandedKursus = false
                                        },
                                    )
                                }
                            }

                        }


                        TextField(
                            value = deskripsi,
                            placeholder = { Text(stringResource(id = R.string.deskripsi)) },
                            onValueChange = {
                                deskripsi = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(176.dp)
                                .padding(top = 14.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFA19B9B),
                                    shape = RoundedCornerShape(size = 6.dp)
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

                        val context = LocalContext.current

                        val listKategori = remember { mutableStateListOf("", "", "") }

                        Row(modifier = Modifier.padding(top = 16.dp)) {

                            listKategori.forEachIndexed { idx, str ->

                                var textKategori by remember { mutableStateOf(str) }
                                val showDialog = remember { mutableStateOf(false) }

                                if (showDialog.value) {
                                    Dialog(onDismissRequest = { showDialog.value = false }) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(24.dp)
                                            ) {

                                                TextField(
                                                    value = textKategori,
                                                    placeholder = { Text("Tambahkan Kategori") },
                                                    onValueChange = {
                                                        if (it.length <= 6) {
                                                            textKategori = it
                                                        }
                                                    },

                                                    shape = RoundedCornerShape(14.dp),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(top = 14.dp)
                                                        .border(
                                                            width = 1.dp,
                                                            color = Color(0xFFA19B9B),
                                                            shape = RoundedCornerShape(size = 14.dp)
                                                        ),
                                                    textStyle = TextStyle(
                                                        fontFamily = PoppinsFamily,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        platformStyle = PlatformTextStyle(
                                                            includeFontPadding = false
                                                        )
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

                                                Button(
                                                    onClick = {
                                                        listKategori[idx] = textKategori
                                                        showDialog.value = false
                                                    },
                                                    modifier = Modifier.padding(top = 12.dp),
                                                    contentPadding = PaddingValues(0.dp),
                                                    colors = ButtonDefaults.buttonColors(secondary)
                                                ) {
                                                    Text(
                                                        text = stringResource(id = R.string.submit),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(
                                                            horizontal = 20.dp,
                                                            vertical = 6.dp
                                                        )
                                                    )
                                                }


                                            }
                                        }

                                    }
                                }

                                if (idx == 0) {
                                    Card(
                                        modifier = Modifier.clickable { showDialog.value = true },
                                        colors = CardDefaults.cardColors(secondary)
                                    ) {
                                        Text(
                                            text = if (str == "") "+ kategori" else str,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(
                                                horizontal = 24.dp,
                                                vertical = 8.dp
                                            )
                                        )

                                    }
                                } else {
                                    if (listKategori[idx - 1] != "") {
                                        Card(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog.value = true
                                                }
                                                .padding(start = 8.dp),
                                            colors = CardDefaults.cardColors(secondary)
                                        ) {
                                            Text(
                                                text = if (str == "") "+ kategori" else str,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White,
                                                modifier = Modifier.padding(
                                                    horizontal = 24.dp,
                                                    vertical = 8.dp
                                                )
                                            )

                                        }
                                    }
                                }

                            }
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 34.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    UploadPortofolio(
                                        imageUri,
                                        title,
                                        listKategori,
                                        deskripsi,
                                        navController,
                                        context,
                                        kursus
                                    )
                                },
                                shape = RoundedCornerShape(size = 6.dp),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(secondary)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.submit),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 20.dp)
                                )

                            }

                        }


                    }
                }
            }


        }

    }

}


fun UploadPortofolio(
    imageUri: Uri?,
    title: String,
    kategori: SnapshotStateList<String>,
    deskripsi: String,
    navController: NavHostController,
    current: Context,
    kursus: String
) {
    if (imageUri != null && title != "" && deskripsi != "" && kursus != "" ) {

        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val id = db.collection("portofolio").document().id

        val storageReference = FirebaseStorage.getInstance().reference
        val loc = storageReference.child("portofolio/$id")
        val uploadTask = loc.putFile(imageUri)
        val timeNow = FieldValue.serverTimestamp()

        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val data =
                    DataPortofolio(
                        id = id,
                        idUser = currentUserId,
                        image = it.toString(),
                        judul = title,
                        kategori = kategori,
                        deskripsi = deskripsi,
                        time = timeNow,
                        idKursus = kursus
                    )
                db.collection("portofolio").document(id)
                    .set(data)
                    .addOnSuccessListener { documentReference ->
                        navController.navigate(Screens.Portofolio.route)
                    }
            }
        }


    } else {
        Toast.makeText(current, "Data Harus Diisi Semua", Toast.LENGTH_LONG).show()
    }


}


