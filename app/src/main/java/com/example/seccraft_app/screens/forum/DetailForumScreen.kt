package com.example.seccraft_app.screens.forum

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.Collection.Forum.ReplyForum
import com.example.seccraft_app.Collection.User.DataUser
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailForumScreen(navController: NavHostController, forumId: String) {
    Accompanist().TopBar(color = primary)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
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

        var forumData by remember { mutableStateOf(ForumCollection()) }
        var replyForum by remember { mutableStateOf(listOf<ReplyForum>()) }

        JetFirestore(
            path = { document("Forum/$forumId") },
            onRealtimeDocumentFetch = { value, exception ->
                val id: String = value!!.getString("id").toString()
                val idUser: String = value.getString("idUser").toString()
                val image: String = value.getString("image").toString()
                val textForum: String = value.getString("textForum").toString()

                forumData =
                    forumData.copy(image = image, id = id, idUser = idUser, TextForum = textForum)
            }
        ) {
            JetFirestore(
                path = { collection("Forum/$forumId/ReplyForum") },
                onRealtimeCollectionFetch = { values, exception ->
                    replyForum = values.getListOfObjects()
                },
            ) {

                if (replyForum.isNotEmpty()) {
                    ForumWithReply(forumData, replyForum, navController)

                } else {
                    ForumDisplay(forumData, navController)

                }


            }

        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumDisplay(forumData: ForumCollection, navController: NavHostController) {

    var user0 by remember { mutableStateOf(DataUser()) }
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowImage(image = forumData.image) {
            showDialog.value = it
        }
    }

    if (forumData.idUser != "") {
        JetFirestore(path = { document("users/${forumData.idUser}") },
            onRealtimeDocumentFetch = { value, exception ->

                val name = value!!.getString("name").toString()
                val image = value.getString("image").toString()

                user0 = user0.copy(image = image, name = name)
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {

                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Card(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = user0.image),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))

                    Column {
                        Text(text = user0.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = forumData.TextForum,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (forumData.image != "") {
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
                                    painter = rememberAsyncImagePainter(model = forumData.image),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            showDialog.value = true
                                        }
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .clickable {
                                    navController.navigate("forum_reply_screen/${forumData.id}/null")
                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.chat),
                                contentDescription = "",
                                modifier = Modifier.size(12.dp)
                            )

                            Text(
                                text = stringResource(id = R.string.balas),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(start = 8.dp)
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
fun ForumWithReply(
    forumData: ForumCollection,
    replyForum: List<ReplyForum>,
    navController: NavHostController
) {

    var user by remember { mutableStateOf(DataUser()) }
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowImage(image = forumData.image) {
            showDialog.value = it
        }
    }

    if (forumData.idUser != "") {

        if (showDialog.value) {
            ShowImage(image = forumData.image) {
                showDialog.value = it
            }
        }

        JetFirestore(path = { document("users/${forumData.idUser}") },
            onRealtimeDocumentFetch = { value, exception ->

                val name = value!!.getString("name").toString()
                val image = value.getString("image").toString()

                user = user.copy(image = image, name = name)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 14.dp),
            ) {
                item {

                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            ConstraintLayout(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(top = 14.dp)
                                    .fillMaxWidth()
                            ) {
                                val (photoRef, contentRef) = createRefs()
                                Column(
                                    modifier = Modifier.constrainAs(photoRef) {
                                        start.linkTo(parent.start)
                                        top.linkTo(contentRef.top)
                                        bottom.linkTo(contentRef.bottom)
                                        height = Dimension.fillToConstraints
                                    },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Card(
                                        modifier = Modifier.size(40.dp),
                                        shape = RoundedCornerShape(180.dp)
                                    ) {
                                        if (user.image == "") {
                                            Image(
                                                painter = painterResource(id = R.drawable.user_profile),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(CircleShape)
                                            )
                                        } else {
                                            Image(
                                                painter = rememberAsyncImagePainter(model = user.image),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(CircleShape)
                                            )
                                        }
                                    }

                                    Divider(
                                        color = black4d,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(2.dp)
                                    )

                                }

                                Column(modifier = Modifier
                                    .padding(start = 16.dp)
                                    .constrainAs(contentRef) {
                                        top.linkTo(parent.top)
                                        start.linkTo(photoRef.end)
                                        end.linkTo(parent.end)
                                        width = Dimension.fillToConstraints
                                    }) {
                                    Text(
                                        text = user.name,
                                        fontFamily = PoppinsFamily,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        style = LocalTextStyle.current.copy(
                                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                                        ),
                                    )



                                    Text(
                                        text = forumData.TextForum,
                                        fontFamily = PoppinsFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Black,
                                        style = LocalTextStyle.current.copy(
                                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                                        ),
                                    )

                                    if (forumData.image != "") {
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
                                                painter = rememberAsyncImagePainter(model = forumData.image),
                                                contentDescription = "",
                                                contentScale = ContentScale.FillBounds,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clickable {
                                                        showDialog.value = true
                                                    }
                                            )
                                        }
                                    }

                                    Row(modifier = Modifier.padding(vertical = 16.dp)) {

                                        Row(modifier = Modifier.clickable {
                                            navController.navigate("forum_reply_screen/${forumData.id}/null")
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.chat),
                                                contentDescription = "",
                                            )

                                            Text(
                                                text = stringResource(id = R.string.balas),
                                                fontFamily = PoppinsFamily,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = gray_25,
                                                style = LocalTextStyle.current.copy(
                                                    platformStyle = PlatformTextStyle(
                                                        includeFontPadding = false
                                                    )
                                                ),
                                                modifier = Modifier.padding(start = 8.dp)
                                            )

                                        }

                                    }
                                }
                            }

                            val showReplyDialog = remember { mutableStateOf(false) }

                            replyForum.forEachIndexed { idx, reply ->

                                if (showReplyDialog.value) {
                                    ShowImage(image = reply.image) {
                                        showReplyDialog.value = it
                                    }
                                }

                                Column(modifier = Modifier.fillMaxSize()) {
                                    ConstraintLayout(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .fillMaxWidth()
                                    ) {
                                        var userReply by remember { mutableStateOf(DataUser()) }
                                        JetFirestore(
                                            path = { document("users/${reply.idUser}") },
                                            onRealtimeDocumentFetch = { value, exception ->
                                                val email = value!!.getString("email").toString()
                                                val name = value.getString("name").toString()
                                                val number = value.getString("number").toString()
                                                val image = value.getString("image").toString()

                                                userReply =
                                                    userReply.copy(image, name, email, number)
                                            }
                                        ) {
                                            val (photoRef, contentRef) = createRefs()
                                            Column(
                                                modifier = Modifier
                                                    .constrainAs(photoRef) {
                                                        start.linkTo(parent.start)
                                                        top.linkTo(contentRef.top)
                                                        bottom.linkTo(contentRef.bottom)
                                                        height = Dimension.fillToConstraints
                                                    },
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Card(
                                                    modifier = Modifier.size(40.dp),
                                                    shape = RoundedCornerShape(180.dp),
                                                ) {
                                                    if (userReply.image == "") {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.user_profile),
                                                            contentDescription = "",
                                                            contentScale = ContentScale.Crop,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .clip(CircleShape)
                                                        )
                                                    } else {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = userReply.image
                                                            ),
                                                            contentDescription = "",
                                                            contentScale = ContentScale.Crop,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .clip(CircleShape)
                                                        )
                                                    }
                                                }
                                                if (idx != (replyForum.size - 1)) {
                                                    Divider(
                                                        color = black4d,
                                                        modifier = Modifier
                                                            .fillMaxHeight()
                                                            .width(2.dp)
                                                    )
                                                }
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .padding(start = 16.dp)
                                                    .padding(bottom = 14.dp)
                                                    .constrainAs(contentRef) {
                                                        top.linkTo(parent.top)
                                                        start.linkTo(photoRef.end)
                                                        end.linkTo(parent.end)
                                                        width = Dimension.fillToConstraints
                                                    }) {
                                                Text(
                                                    text = userReply.name,
                                                    fontFamily = PoppinsFamily,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black,
                                                    style = LocalTextStyle.current.copy(
                                                        platformStyle = PlatformTextStyle(
                                                            includeFontPadding = false
                                                        )
                                                    ),
                                                )
                                                Text(
                                                    text = reply.TextReply,
                                                    fontFamily = PoppinsFamily,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    color = Color.Black,
                                                    style = LocalTextStyle.current.copy(
                                                        platformStyle = PlatformTextStyle(
                                                            includeFontPadding = false
                                                        )
                                                    ),
                                                )

                                                if (reply.image != "") {
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
                                                            painter = rememberAsyncImagePainter(model = reply.image),
                                                            contentDescription = "",
                                                            contentScale = ContentScale.FillBounds,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .clickable {
                                                                    showReplyDialog.value = true

                                                                }
                                                        )
                                                    }
                                                }

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .padding(top = 8.dp)
                                                        .clickable {
                                                            navController.navigate("forum_reply_screen/${forumData.id}/${reply.id}")
                                                        }
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.chat),
                                                        contentDescription = "",
                                                    )

                                                    Text(
                                                        text = stringResource(id = R.string.balas),
                                                        fontFamily = PoppinsFamily,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Normal,
                                                        color = gray_25,
                                                        style = LocalTextStyle.current.copy(
                                                            platformStyle = PlatformTextStyle(
                                                                includeFontPadding = false
                                                            )
                                                        ),
                                                        modifier = Modifier.padding(start = 8.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }


                            }
                            Divider(color = black4d, thickness = 1.dp)
                        }
                    }

                }
            }
        }
    }

}
