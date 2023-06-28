package com.example.seccraft_app.screens.portofolio


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.User.DataUser
import com.example.seccraft_app.Collection.portofolio.DataPortofolio
import com.example.seccraft_app.Collection.portofolio.LikePortofolio
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortofolioScreen(
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Card(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 25.dp,
                bottomEnd = 25.dp
            ),
            colors = CardDefaults.cardColors(
                primary
            )
        ) {


        }
        Column(modifier = Modifier.padding(top = 28.dp)) {
            Text(
                text = stringResource(id = R.string.portofolio),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 28.dp, start = 20.dp)
            )

            var kategoriText by remember { mutableStateOf("") }
            TextField(
                value = kategoriText,
                readOnly = true,
                placeholder = { Text(stringResource(id = R.string.kategori)) },
                onValueChange = {
                    kategoriText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                textStyle = MaterialTheme.typography.labelMedium,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(18.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
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

            // Terkini dan populer

            var selected by remember {
                mutableStateOf(true)
            }

            Row(modifier = Modifier.padding(start = 20.dp, top = 28.dp)) {
                Card(
                    modifier = Modifier
//                        .width(72.dp)
//                        .height(35.dp)
                        .clickable {
                            selected = true
                        },
                    colors = if (selected) CardDefaults.cardColors(secondary) else CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.terkini),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (selected) Color.White else secondary,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            selected = false
                        },
                    colors = if (!selected) CardDefaults.cardColors(secondary) else CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.populer),
                        color = if (!selected) Color.White else secondary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                    )
                }

            }

            var dataPortofolio by remember { mutableStateOf(listOf<DataPortofolio>()) }

            JetFirestore(
                path = { collection("portofolio") },
                onRealtimeCollectionFetch = { values, exception ->
                    dataPortofolio = values.getListOfObjects()
                },
            ) {
                //item Pola
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(top = 12.dp, start = 20.dp, end = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    items(dataPortofolio) { portofolio ->
                        var user by remember { mutableStateOf(DataUser()) }
                        JetFirestore(
                            path = { document("users/${portofolio.idUser}") },
                            onRealtimeDocumentFetch = { value, exception ->
                                val email = value!!.getString("email").toString()
                                val name = value.getString("name").toString()
                                val number = value.getString("number").toString()
                                val image = value.getString("image").toString()

                                user = user.copy(image, name, email, number)
                            }
                        ) {

                            val name = user.name.substringBefore(' ')

                            val title = if (portofolio.judul.length < 8) {
                                portofolio.judul.substring(0, portofolio.judul.length)
                            } else {
                                portofolio.judul.substring(0, 8) + "..."
                            }

                            Log.d("itung String", "PortofolioScreen: ${title}")

                            CardItem(
                                id = portofolio.id,
                                idUser = portofolio.idUser,
                                image = portofolio.image,
                                title = title,
                                name = name
                            )

                        }
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(image: String, title: String, name: String, idUser: String, id: String) {

    var likeCount by remember { mutableStateOf(0) }
    var dataLike by remember { mutableStateOf(listOf<LikePortofolio>()) }

    JetFirestore(
        path = { collection("portofolio/$id/like") },
        onRealtimeCollectionFetch = { values, exception ->
            dataLike = values.getListOfObjects()
        },
    ) {

        likeCount = dataLike.count {
            it.like
        }

        Card(
            colors = CardDefaults.cardColors(primary),
            modifier = Modifier.wrapContentHeight(),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color.Gray),
                    shape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = image),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 14.dp, top = 8.dp)
                ) {
                    Column() {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    val auth = Firebase.auth
                    val idCurrentUser = auth.currentUser!!.uid
                    var likeData by remember { mutableStateOf(LikePortofolio()) }

                    JetFirestore(
                        path = { document("portofolio/$id/like/$idCurrentUser") },
                        onRealtimeDocumentFetch = { value, exception ->

                            val idUserLike = value!!.getString("idUser").toString()
                            val like = value.getBoolean("like")

                            likeData = if (like == null) likeData.copy(
                                idUserLike,
                                false
                            ) else likeData.copy(idUserLike, like)

                            Log.d("Isi Like", "CardItem: $like")
                        }
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.love),
                                contentDescription = "",
                                tint = if (likeData.like) Color.Red else Color.Black,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (likeData.like) {
                                            LikePto(id, false)
                                        } else {
                                            LikePto(id, true)
                                        }
                                    }
                            )
                            Text(
                                text = likeCount.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                }


            }

        }
    }

}

fun LikePto(id: String, like: Boolean) {
    val auth = Firebase.auth
    val db = Firebase.firestore
    val idCurrentUser = auth.currentUser!!.uid

    val data = LikePortofolio(idUser = idCurrentUser, like = like)

    db.collection("portofolio/$id/like/").document(idCurrentUser).set(data)


}

//@Preview(showBackground = true, name = "Pola preview")
//@Composable
//fun PolaPreview() {
//    SeccraftappTheme {
//        Pola()
//    }
//}
