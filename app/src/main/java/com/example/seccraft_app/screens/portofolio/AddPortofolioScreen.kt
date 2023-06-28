package com.example.seccraft_app.screens.portofolio

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
import com.example.seccraft_app.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.portofolio.DataPortofolio
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

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
                Text(
                    text = stringResource(id = R.string.portofolio),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 20.dp, top = 28.dp)
                )
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
                        var kategori by remember { mutableStateOf("") }
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
                            value = kategori,
                            placeholder = { Text(stringResource(id = R.string.kategori)) },
                            onValueChange = {
                                kategori = it
                            },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_down),
                                    contentDescription = "",
                                    tint = Color(0xFFA19B9B)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
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
                                        kategori,
                                        deskripsi,
                                        navController,
                                        context
                                    )
                                },
                                shape = RoundedCornerShape(size = 6.dp),
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
    kategori: String,
    deskripsi: String,
    navController: NavHostController,
    current: Context
) {
    if (imageUri != null && title != ""  && deskripsi != "") {

        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val id = db.collection("portofolio").document().id

        val storageReference = FirebaseStorage.getInstance().reference
        val loc = storageReference.child("Portofolio/$id")
        val uploadTask = loc.putFile(imageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val data =
                    DataPortofolio(id, currentUserId, it.toString(), title, kategori, deskripsi)
                db.collection("portofolio").document(id)
                    .set(data)
                    .addOnSuccessListener { documentReference ->
                        navController.navigate(BottomBarScreen.Portofolio.route)
                    }

            }
        }


    } else {
        Toast.makeText(current, "Data Harus Diisi Semua", Toast.LENGTH_LONG).show()
    }


}
