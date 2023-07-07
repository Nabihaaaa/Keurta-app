package com.example.seccraft_app.screens.artikel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.artikel.DataArtikel
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArtikelSceen(
    navController: NavHostController,
    dataArtikelModel: DataArtikelModel = viewModel()
) {

    val pembuat = dataArtikelModel.pembuat.value

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(primary),
                shape = RectangleShape
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 24.dp, bottom = 20.dp),
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
                        text = stringResource(id = R.string.artikel),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }

            LazyColumn {
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

                        TextField(
                            value = deskripsi,
                            placeholder = { Text(stringResource(id = R.string.konten)) },
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 34.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            val context = LocalContext.current
                            Button(
                                onClick = {

                                    addArtikel(
                                        title,
                                        deskripsi,
                                        imageUri,
                                        navController,
                                        context,
                                        pembuat
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

fun addArtikel(
    title: String,
    deskripsi: String,
    imageUri: Uri?,
    navController: NavHostController,
    context: Context,
    pembuat: String
) {
    if (imageUri != null && title != "" && deskripsi != "") {

        val db = Firebase.firestore
        val id = db.collection("artikel").document().id

        val storageReference = FirebaseStorage.getInstance().reference
        val loc = storageReference.child("Artikel/$id/potoArtikel")
        val uploadTask = loc.putFile(imageUri)
        val timeNow = FieldValue.serverTimestamp()

        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val data =
                    DataArtikel(
                        id = id,
                        image = it.toString(),
                        deskripsi = deskripsi,
                        title = title,
                        time = timeNow,
                        pembuat = pembuat
                    )
                db.collection("artikel").document(id)
                    .set(data)
                    .addOnSuccessListener { documentReference ->
                        navController.navigate(Screens.Artikel.route)
                    }
            }
        }
    }else {
        Toast.makeText(context, "Data Harus Diisi Semua", Toast.LENGTH_LONG).show()
    }

}
