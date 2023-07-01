package com.example.seccraft_app.screens.forum

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(navController: NavHostController) {

    var textForum by remember { mutableStateOf("") }

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


            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 32.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 30.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.topik_baru),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp,
                        color = secondary
                    )

                    TextField(
                        value = textForum,
                        placeholder = { Text(stringResource(id = R.string.tuliskan_topik)) },
                        onValueChange = {
                            textForum = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(top = 20.dp)
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
                    Row (modifier = Modifier.fillMaxWidth()){
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                val text = textForum
                                uploadText(text, navController)
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(secondary),
                            modifier = Modifier.padding(top = 12.dp, bottom = 24.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.submit),
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

        }
    }
}

private fun uploadText(text: String, navController: NavHostController) {

    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val timeNow = FieldValue.serverTimestamp()

    val id = db.collection("Forum").document().id
    val data =
        ForumCollection(id = id, idUser = currentUser!!.uid, TextForum = text, time = timeNow)

    db.collection("Forum").document(id)
        .set(data)
        .addOnSuccessListener { documentReference ->
            navController.navigate(BottomBarScreen.Forum.route)
        }
        .addOnFailureListener { e ->

        }

}

//@Preview(showBackground = true, name = "Forum preview Text")
//@Composable
//fun TextScreenPrv() {
//    TextScreen(navController)
//}
