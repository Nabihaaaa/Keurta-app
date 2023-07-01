package com.example.seccraft_app.screens.forum

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.Collection.Forum.ReplyForum
import com.example.seccraft_app.Collection.User.DataUser
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.jet.firestore.JetFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(navController: NavHostController, documentId: String, replyId: String) {

    var textForum by remember { mutableStateOf("") }
    var dataForum by remember { mutableStateOf(ForumCollection()) }
    var dataReply by remember { mutableStateOf(ReplyForum()) }
    var dataUserForum by remember { mutableStateOf(DataUser()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
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
                            navController.navigate(BottomBarScreen.Forum.route)
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.forum),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 26.dp)
                    )

                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp),
            ) {
                item {

                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    ) {

                        Column(Modifier.padding(top = 14.dp, start = 12.dp, end = 12.dp)) {

                            JetFirestore(
                                path = {
                                    if (replyId == "null") document("Forum/$documentId")
                                    else document(
                                        "Forum/$documentId/ReplyForum/$replyId"
                                    )
                                },
                                onSingleTimeDocumentFetch = { value, exception ->

                                    if (replyId == "null") {
                                        val id: String = value!!.getString("id").toString()
                                        val idUser: String = value.getString("idUser").toString()
                                        val image: String = value.getString("image").toString()
                                        val text: String = value.getString("textForum").toString()

                                        dataForum = dataForum.copy(
                                            image = image,
                                            id = id,
                                            idUser = idUser,
                                            TextForum = text
                                        )
                                    } else {
                                        val id: String = value!!.getString("id").toString()
                                        val idUser: String = value.getString("idUser").toString()
                                        val image: String = value.getString("image").toString()
                                        val text: String = value.getString("textReply").toString()

                                        dataReply = dataReply.copy(
                                            image = image,
                                            id = id,
                                            idUser = idUser,
                                            TextReply = text
                                        )

                                    }


                                }
                            ) {
                                Log.d("dataReply", "ReplyScreen: $dataReply")
                                if (dataReply.id != "") {
                                    JetFirestore(
                                        path = { document("users/${dataReply.idUser}") },
                                        onSingleTimeDocumentFetch = { value, exception ->
                                            val name = value!!.getString("name").toString()
                                            val image = value.getString("image").toString()

                                            dataUserForum =
                                                dataUserForum.copy(image = image, name = name)
                                        }
                                    ) {
                                        Row(modifier = Modifier.padding(bottom = 18.dp)) {
                                            Card(
                                                modifier = Modifier.size(40.dp),
                                                shape = CircleShape
                                            ) {
                                                Image(
                                                    painter = rememberAsyncImagePainter(model = dataUserForum.image),
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(start = 12.dp))

                                            Column {
                                                Text(
                                                    text = dataUserForum.name,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = dataReply.TextReply,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                )

                                                if (dataReply.image != "") {
                                                    Card(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(top = 2.dp)
                                                            .height(160.dp),
                                                        colors = CardDefaults.cardColors(
                                                            gray_DA
                                                        )
                                                    ) {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = dataReply.image
                                                            ),
                                                            contentDescription = "",
                                                            contentScale = ContentScale.FillBounds,
                                                            modifier = Modifier.fillMaxSize()

                                                        )
                                                    }
                                                }

                                            }
                                        }

                                        Text(
                                            buildAnnotatedString {
                                                append(stringResource(id = R.string.membalas))
                                                append(" ")
                                                withStyle(style = SpanStyle(color = Color.Blue)) {
                                                    append("@${dataUserForum.name}")
                                                }

                                            },
                                            modifier = Modifier.padding(top = 8.dp),
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Card(
                                            modifier = Modifier
                                                .clickable {
                                                    launcher.launch("image/*")
                                                }
                                                .height(260.dp)
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            colors = CardDefaults.cardColors(Color(0xA3B9B9B9)),
                                            shape = RectangleShape
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
                                                        modifier = Modifier.padding(top = 12.dp)
                                                    )
                                                }
                                            }

                                        }

                                        TextField(
                                            value = textForum,
                                            placeholder = { Text(stringResource(id = R.string.komentar_forum)) },
                                            onValueChange = {
                                                textForum = it
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(146.dp)
                                                .padding(top = 8.dp)
                                                .border(
                                                    border = BorderStroke(
                                                        2.dp,
                                                        Color(0xFFA19B9B)
                                                    ), shape = RoundedCornerShape(6.dp)
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

                                        Row {
                                            Spacer(modifier = Modifier.weight(1f))
                                            Button(
                                                onClick = {
                                                    val text = textForum
                                                    uploadText(
                                                        text,
                                                        navController,
                                                        documentId,
                                                        dataUserForum.name,
                                                        imageUri,
                                                    )
                                                },
                                                shape = RoundedCornerShape(5.dp),
                                                colors = ButtonDefaults.buttonColors(secondary),
                                                modifier = Modifier.padding(
                                                    top = 24.dp,
                                                    bottom = 40.dp
                                                ),
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.post),
                                                    fontFamily = PoppinsFamily,
                                                    fontSize = 12.sp,
                                                    style = LocalTextStyle.current.copy(
                                                        platformStyle = PlatformTextStyle(
                                                            includeFontPadding = false
                                                        )
                                                    ),
                                                    fontWeight = FontWeight(600),
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                } else if (dataForum.idUser != "" && replyId == "null") {
                                    JetFirestore(
                                        path = { document("users/${dataForum.idUser}") },
                                        onSingleTimeDocumentFetch = { value, exception ->
                                            val name = value!!.getString("name").toString()
                                            val image = value.getString("image").toString()

                                            dataUserForum =
                                                dataUserForum.copy(image = image, name = name)
                                        }
                                    ) {
                                        Row(modifier = Modifier.padding(bottom = 18.dp)) {
                                            Card(
                                                modifier = Modifier.size(40.dp),
                                                shape = CircleShape
                                            ) {
                                                Image(
                                                    painter = rememberAsyncImagePainter(model = dataUserForum.image),
                                                    contentDescription = "",
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(start = 12.dp))

                                            Column {
                                                Text(
                                                    text = dataUserForum.name,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = dataForum.TextForum,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                )
                                                if (dataForum.image != "") {
                                                    Card(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(top = 2.dp)
                                                            .height(160.dp),
                                                        colors = CardDefaults.cardColors(
                                                            gray_DA
                                                        )
                                                    ) {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = dataForum.image
                                                            ),
                                                            contentDescription = "",
                                                            contentScale = ContentScale.FillBounds,
                                                            modifier = Modifier.fillMaxSize()

                                                        )
                                                    }
                                                }

                                            }
                                        }

                                        Text(
                                            buildAnnotatedString {
                                                append(stringResource(id = R.string.membalas))
                                                append(" ")
                                                withStyle(style = SpanStyle(color = Color.Blue)) {
                                                    append("@${dataUserForum.name}")
                                                }

                                            },
                                            modifier = Modifier.padding(top = 8.dp),
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Card(
                                            modifier = Modifier
                                                .clickable {
                                                    launcher.launch("image/*")
                                                }
                                                .height(260.dp)
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            colors = CardDefaults.cardColors(Color(0xA3B9B9B9)),
                                            shape = RectangleShape
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
                                                        modifier = Modifier.padding(top = 12.dp)
                                                    )
                                                }
                                            }

                                        }

                                        TextField(
                                            value = textForum,
                                            placeholder = { Text(stringResource(id = R.string.komentar_forum)) },
                                            onValueChange = {
                                                textForum = it
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(146.dp)
                                                .padding(top = 8.dp)
                                                .border(
                                                    border = BorderStroke(
                                                        2.dp,
                                                        Color(0xFFA19B9B)
                                                    ), shape = RoundedCornerShape(6.dp)
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

                                        Row {
                                            Spacer(modifier = Modifier.weight(1f))
                                            Button(
                                                onClick = {
                                                    val text = textForum
                                                    uploadText(
                                                        text,
                                                        navController,
                                                        documentId,
                                                        dataUserForum.name,
                                                        imageUri
                                                    )
                                                },
                                                shape = RoundedCornerShape(5.dp),
                                                colors = ButtonDefaults.buttonColors(secondary),
                                                modifier = Modifier.padding(
                                                    top = 24.dp,
                                                    bottom = 40.dp
                                                ),
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.post),
                                                    fontFamily = PoppinsFamily,
                                                    fontSize = 12.sp,
                                                    style = LocalTextStyle.current.copy(
                                                        platformStyle = PlatformTextStyle(
                                                            includeFontPadding = false
                                                        )
                                                    ),
                                                    fontWeight = FontWeight(600),
                                                    color = Color.White
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
}


private fun uploadText(
    text: String,
    navController: NavHostController,
    documentId: String,
    name: String,
    image: Uri?,
) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    val id = db.collection("Forum/$documentId/ReplyForum").document().id
    val timeNow = FieldValue.serverTimestamp()

    var data =
        ReplyForum(id = id, idUser = currentUser!!.uid, TextReply = "@$name\n$text", time = timeNow)

    if (image != null) {

        val storageReference = FirebaseStorage.getInstance().reference
        val loc = storageReference.child("ReplyForum/$documentId/$id")
        val uploadTask = loc.putFile(image)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                data = ReplyForum(
                    id = id,
                    idUser = currentUser!!.uid,
                    TextReply = "@$name\n$text",
                    time = timeNow,
                    image = it.toString()
                )
                db.collection("Forum/$documentId/ReplyForum").document(id)
                    .set(data)
                    .addOnSuccessListener { documentReference ->
                        navController.navigate(BottomBarScreen.Forum.route)
                    }
                    .addOnFailureListener { e ->

                    }
            }
        }

    } else {
        db.collection("Forum/$documentId/ReplyForum").document(id)
            .set(data)
            .addOnSuccessListener { documentReference ->
                navController.navigate(BottomBarScreen.Forum.route)
            }
            .addOnFailureListener { e ->

            }
    }


}