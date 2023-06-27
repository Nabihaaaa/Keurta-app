package com.example.seccraft_app.screens.forum

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.PoppinsFamily
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.icon_faded
import com.example.seccraft_app.ui.theme.secondary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageForumScreen(navController: NavHostController) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    LaunchedEffect(launcher){
        launcher.launch("image/*")
    }

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(
            modifier = Modifier
                .padding(vertical = 26.dp, horizontal = 12.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(id = R.string.textKomentar),
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            var textForum by remember { mutableStateOf("") }
            TextField(
                value = textForum,
                placeholder = { Text(stringResource(id = R.string.Edittext)) },
                onValueChange = {
                    textForum = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 26.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
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
            if(imageUri != null){
                Card(
                    modifier = Modifier.size(150.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }

            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                colors = ButtonDefaults.buttonColors(secondary),
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(
                    text = "Ubah Gambar",
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    fontWeight = FontWeight(600),
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    val text = textForum
                    uploadImage(text, navController, imageUri)
                },
                colors = ButtonDefaults.buttonColors(secondary),
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.upload_ke_forum),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    fontWeight = FontWeight(600),
                    color = Color.White
                )
            }

        }
    }
}

fun uploadImage(text: String, navController: NavHostController, imageUri: Uri?) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    val id = db.collection("Forum").document().id
    var dataForum = ForumCollection(id = id,idUser = currentUser!!.uid, TextForum = text)

    val storageReference = FirebaseStorage.getInstance().reference
    val loc = storageReference.child("Forum/$id/ForumImage")
    val uploadTask = loc.putFile(imageUri!!)

    uploadTask.addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
            dataForum = dataForum.copy(image = it.toString())
            db.collection("Forum").document(id)
                .set(dataForum)
                .addOnSuccessListener { documentReference ->
                    navController.navigate(BottomBarScreen.Forum.route)
                }
                .addOnFailureListener { e ->

                }
        }

    }.addOnFailureListener {
//        Toast.makeText(context, "Failed to Upload", Toast.LENGTH_SHORT).show()
//        if (progressDialog.isShowing) {
//            progressDialog.dismiss()
//        }
    }
}


