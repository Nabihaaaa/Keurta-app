package com.example.seccraft_app.screens.kursus.add

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.DataAlatdanBahan
import com.example.seccraft_app.collection.kursus.DataKursus
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.screens.util.LinkText
import com.example.seccraft_app.screens.util.LinkTextData
import com.example.seccraft_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddKursusScreen(navController: NavHostController) {
    Accompanist().TopBar(color = primary)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    var harga by remember {
        mutableStateOf("")
    }
    var title by remember {
        mutableStateOf("")
    }
    var deskripsi by remember {
        mutableStateOf("")
    }

    //variable bahan
    var bahan by remember {
        mutableStateOf("")
    }
    var linkBahan by remember {
        mutableStateOf("")
    }
    val listBahan = remember {
        mutableStateListOf<DataAlatdanBahan>()
    }

    //variable alat
    var alat by remember {
        mutableStateOf("")
    }
    var linkAlat by remember {
        mutableStateOf("")
    }
    val listAlat = remember {
        mutableStateListOf<DataAlatdanBahan>()
    }

    //variable level
    var expandedLevel by remember {
        mutableStateOf(false)
    }
    var level by remember {
        mutableStateOf("")
    }
    val listLevel =
        listOf("Level Spesialis", "Level Menengah", "Level Pemula")

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val dataKursus = DataKursus(
        title = title,
        deskripsi = deskripsi,
        level = level,
        harga = if (harga == "") -1 else harga.toLong()
    )

    Scaffold(bottomBar = { BottomBarAddKursusScreen(dataKursus, listAlat, listBahan, context, imageUri, navController) }) {
        LazyColumn {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it), color = bg
                ) {
                    Column {
                        //top
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
                                            navController.popBackStack()
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
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 48.dp)
                        ) {
                            //image
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
                            //judul

                            OutlinedTextField(
                                value = title,
                                placeholder = { Text(text = stringResource(id = R.string.judul)) },
                                onValueChange = { title = it },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(6.dp),
                                textStyle = MaterialTheme.typography.labelMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    placeholderColor = icon_faded,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = primary,
                                    unfocusedBorderColor = icon_faded
                                )
                            )
                            //deskripsi

                            OutlinedTextField(
                                value = deskripsi,
                                placeholder = { Text(text = stringResource(id = R.string.deskripsi)) },
                                onValueChange = { deskripsi = it },
                                singleLine = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .height(176.dp),
                                shape = RoundedCornerShape(6.dp),
                                textStyle = MaterialTheme.typography.labelMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    placeholderColor = icon_faded,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = primary,
                                    unfocusedBorderColor = icon_faded
                                )
                            )
                            //level

                            Column(modifier = Modifier.wrapContentHeight()) {
                                OutlinedTextField(
                                    value = level,
                                    placeholder = {
                                        Text(
                                            text = stringResource(id = R.string.level),
                                            modifier = Modifier.clickable {
                                                expandedLevel = !expandedLevel
                                            })
                                    },
                                    onValueChange = {
                                        level = it
                                    },
                                    singleLine = true,
                                    readOnly = true,
                                    trailingIcon = {
                                        if (expandedLevel)
                                            Icon(
                                                painter = painterResource(id = R.drawable.arrow_down),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .rotate(180f)
                                                    .clickable {
                                                        expandedLevel = !expandedLevel
                                                    }
                                            )
                                        else
                                            Icon(
                                                painter = painterResource(id = R.drawable.arrow_down),
                                                contentDescription = "",
                                                modifier = Modifier.clickable {
                                                    expandedLevel = !expandedLevel
                                                }
                                            )
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 14.dp)
                                        .onGloballyPositioned { layoutCoordinates ->
                                            textFieldSize = layoutCoordinates.size.toSize()
                                        },
                                    textStyle = MaterialTheme.typography.labelMedium,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        placeholderColor = icon_faded,
                                        containerColor = Color.Transparent,
                                        cursorColor = Color.Black,
                                        focusedIndicatorColor = primary,
                                        unfocusedIndicatorColor = icon_faded
                                    )
                                )

                                DropdownMenu(
                                    expanded = expandedLevel,
                                    onDismissRequest = { expandedLevel = false },
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                                ) {
                                    listLevel.forEach { label ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = label,
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            },
                                            onClick = {
                                                level = label
                                                expandedLevel = false
                                            },
                                        )
                                    }
                                }

                            }
                            //Harga

                            OutlinedTextField(
                                value = harga,
                                placeholder = { Text(text = stringResource(id = R.string.harga)) },
                                onValueChange = {
                                    val regex = Regex("[0-9]*")
                                    if (it.matches(regex)) {
                                        harga = it
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(6.dp),
                                textStyle = MaterialTheme.typography.labelMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    placeholderColor = icon_faded,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = primary,
                                    unfocusedBorderColor = icon_faded
                                )
                            )
                            //alat
                            Text(
                                text = stringResource(id = R.string.alat),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            OutlinedTextField(
                                value = alat,
                                placeholder = { Text(text = stringResource(id = R.string.alat)) },
                                onValueChange = { alat = it },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(6.dp),
                                textStyle = MaterialTheme.typography.labelMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    placeholderColor = icon_faded,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = primary,
                                    unfocusedBorderColor = icon_faded
                                )
                            )
                            if (alat != "") {
                                OutlinedTextField(
                                    value = linkAlat,
                                    placeholder = { Text(text = stringResource(id = R.string.link_alat)) },
                                    onValueChange = {
                                        linkAlat = it
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    textStyle = MaterialTheme.typography.labelMedium,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black,
                                        placeholderColor = icon_faded,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = primary,
                                        unfocusedBorderColor = icon_faded
                                    )
                                )
                            }

                            Button(
                                onClick = {
                                    if (alat != "") {
                                        if (linkAlat == "") {
                                            listAlat.add(
                                                DataAlatdanBahan(
                                                    nama = alat,
                                                    link = linkAlat
                                                )
                                            )
                                            alat = ""
                                            linkAlat = ""
                                        } else {
                                            if (URLUtil.isValidUrl(linkAlat)) {
                                                listAlat.add(
                                                    DataAlatdanBahan(
                                                        nama = alat,
                                                        link = linkAlat
                                                    )
                                                )
                                                alat = ""
                                                linkAlat = ""
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Link yang diinputkan tidak valid",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Mohon Isi Alat terlebih dahulu",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    secondary
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Tambahkan Alat",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White,
                                )
                            }

                            listAlat.forEachIndexed { idx, it ->
                                Row {
                                    LinkText(
                                        style = MaterialTheme.typography.bodyLarge,
                                        linkTextData = listOf(
                                            LinkTextData(
                                                text = "  ${idx + 1}. ${it.nama} "
                                            ),
                                            LinkTextData(
                                                text = "(Link)",
                                                tag = "link_olshop",
                                                annotation = it.link,
                                                onClick = { uri ->
                                                    if (uri.item != "") {
                                                        val openURL = Intent(Intent.ACTION_VIEW)
                                                        openURL.data = Uri.parse(uri.item)
                                                        ContextCompat.startActivity(
                                                            context,
                                                            openURL,
                                                            null
                                                        )
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
                                    Icon(
                                        painter = painterResource(id = R.drawable.close),
                                        contentDescription = "",
                                        tint = Color.Red,
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .size(16.dp)
                                            .clickable {
                                                listAlat.removeAt(idx)
                                            }
                                    )
                                }
                            }

                            //bahan
                            Text(
                                text = stringResource(id = R.string.bahan),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            OutlinedTextField(
                                value = bahan,
                                placeholder = { Text(text = stringResource(id = R.string.bahan)) },
                                onValueChange = { bahan = it },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(6.dp),
                                textStyle = MaterialTheme.typography.labelMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = Color.Black,
                                    placeholderColor = icon_faded,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = primary,
                                    unfocusedBorderColor = icon_faded
                                )
                            )
                            if (bahan != "") {
                                OutlinedTextField(
                                    value = linkBahan,
                                    placeholder = { Text(text = stringResource(id = R.string.link_bahan)) },
                                    onValueChange = {
                                        linkBahan = it
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    textStyle = MaterialTheme.typography.labelMedium,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black,
                                        placeholderColor = icon_faded,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = primary,
                                        unfocusedBorderColor = icon_faded
                                    )
                                )
                            }

                            Button(
                                onClick = {
                                    if (bahan != "") {
                                        if (linkBahan == "") {
                                            listBahan.add(
                                                DataAlatdanBahan(
                                                    nama = bahan,
                                                    link = linkBahan
                                                )
                                            )
                                            bahan = ""
                                            linkBahan = ""
                                        } else {
                                            if (URLUtil.isValidUrl(linkBahan)) {
                                                listBahan.add(
                                                    DataAlatdanBahan(
                                                        nama = bahan,
                                                        link = linkBahan
                                                    )
                                                )
                                                bahan = ""
                                                linkBahan = ""
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Link yang diinputkan tidak valid",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Mohon Isi Bahan terlebih dahulu",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    secondary
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Tambahkan Bahan",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White,
                                )
                            }

                            listBahan.forEachIndexed { idx, it ->
                                Row {
                                    LinkText(
                                        style = MaterialTheme.typography.bodyLarge,
                                        linkTextData = listOf(
                                            LinkTextData(
                                                text = "  ${idx + 1}. ${it.nama} "
                                            ),
                                            LinkTextData(
                                                text = "(Link)",
                                                tag = "link_olshop",
                                                annotation = it.link,
                                                onClick = { uri ->
                                                    if (uri.item != "") {
                                                        val openURL = Intent(Intent.ACTION_VIEW)
                                                        openURL.data = Uri.parse(uri.item)
                                                        ContextCompat.startActivity(
                                                            context,
                                                            openURL,
                                                            null
                                                        )
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
                                    Icon(
                                        painter = painterResource(id = R.drawable.close),
                                        contentDescription = "",
                                        tint = Color.Red,
                                        modifier = Modifier.padding(start = 8.dp).size(16.dp).clickable {
                                            listBahan.removeAt(idx)
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

}

@Composable
fun BottomBarAddKursusScreen(
    dataKursus: DataKursus,
    listAlat: SnapshotStateList<DataAlatdanBahan>,
    listBahan: SnapshotStateList<DataAlatdanBahan>,
    context: Context,
    imageUri: Uri?,
    navController: NavHostController
) {
    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            Button(
                onClick = {
                    if (imageUri != null && dataKursus.title != ""
                        && dataKursus.deskripsi != "" && dataKursus.level != ""
                        && dataKursus.harga >= 0 && listBahan.size > 0 && listAlat.size > 0) {
                        UploadKursus(dataKursus, listAlat, listBahan, imageUri,context, navController)
                    } else {
                        Toast.makeText(context, "Data Harus Diisi Semua!", Toast.LENGTH_LONG).show()
                    }

                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    secondary
                ),
                shape = RoundedCornerShape(15.dp)
            ) {

                Text(
                    text = "Simpan",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

            }
        }
    }
}

