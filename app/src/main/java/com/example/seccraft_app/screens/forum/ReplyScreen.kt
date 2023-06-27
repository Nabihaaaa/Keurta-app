package com.example.seccraft_app.screens.forum

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.Collection.Forum.ReplyForum
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.PoppinsFamily
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.icon_faded
import com.example.seccraft_app.ui.theme.secondary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ReplyScreen(navController: NavHostController, documentId: String) {

    Log.d("ISI String apa", "ReplyScreen: $documentId")

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(
            modifier = Modifier
                .padding(vertical = 26.dp, horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.text),
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            var textForum by remember { mutableStateOf("") }
            TextField(
                value = textForum,
                placeholder = { Text(stringResource(id = R.string.komentar_forum)) },
                onValueChange = {
                    textForum = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp)
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

            Button(
                onClick = {
                    val text = textForum
                    uploadText(text, navController, documentId)
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

private fun uploadText(text: String, navController: NavHostController, documentId: String) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    val id = db.collection("Forum").document().id
    val data = ReplyForum(id = id,idUser = currentUser!!.uid, TextReply = text)

    db.collection("Forum/$documentId/ReplyForum").document(id)
        .set(data)
        .addOnSuccessListener { documentReference ->
            navController.navigate(BottomBarScreen.Forum.route)
        }
        .addOnFailureListener { e ->

        }

}