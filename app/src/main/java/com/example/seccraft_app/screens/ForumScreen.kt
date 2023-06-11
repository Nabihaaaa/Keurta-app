package com.example.seccraft_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.seccraft_app.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.seccraft_app.Collection.Forum.ForumCollection
import com.example.seccraft_app.Collection.Forum.ReplyForum
import com.example.seccraft_app.ui.theme.*

@Composable
fun ForumScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        TopForum()
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SearchForum()
            ItemForum()
        }
    }


}

@Composable
fun ItemForum() {
    var replayforum = mutableListOf(
        ReplyForum(
            "0",
            "0",
            "Muharim Awaluddin",
            "",
            null,
            "kamu bisa ganti jarum jahitnya ke kualitas yang lebih bagus",
        ),
        ReplyForum(
            "0",
            "0",
            "Annisa Nur Iksan",
            "",
            null,
            "kamu perlu buku pedoman untuk mengganti jarum itu. Kalau sudah terpasang, kamu harus menyesuaikan jarum dengan jenis kain yang kamu pilih.",
        ),
    )
    var listforum = mutableListOf<ForumCollection>(
        ForumCollection(
            "0",
            "0",
            "Karunia Agustiani",
            "",
            null,
            "kalau saat menjahit dan jarumnya sering patah. Bagaimana ya mengatasi itu?",
            replayforum
        ),
        ForumCollection(
            "1",
            "1",
            "Muharim Awaluddin",
            "",
            null,
            "kalau mengatasi benang terlilit bagaimana ya?",
            null
        ),
        ForumCollection(
            "1",
            "1",
            "Laila Fallah",
            "",
            null,
            "kalau ingin buat cardigan kayak begini, satu kotak itu biasanya ukuran berapa ya ?",
            null
        ),
    )
    LazyColumn(
        //contentPadding = PaddingValues(top = 16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical =16.dp)
    ) {
        items(listforum) { forum ->
            CardItemForum(forum)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemForum(item: ForumCollection) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = 8.dp).padding(top=14.dp)
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
                    Card(modifier = Modifier.size(40.dp), shape = RoundedCornerShape(180.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.anya),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    if(item.reply!=null){
                        Divider(
                            color = black4d,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(start = 16.dp).constrainAs(contentRef){
                    top.linkTo(parent.top)
                    start.linkTo(photoRef.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }) {
                    Text(
                        text = item.username,
                        fontFamily = PoppinsFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        ),
                    )
                    Text(
                        text = item.desc,
                        fontFamily = PoppinsFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        ),
                    )
                    Row(modifier = Modifier.padding(vertical = 16.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat),
                            contentDescription = ""
                        )
                        Text(
                            text = stringResource(id = R.string.balas),
                            fontFamily = PoppinsFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = gray_25,
                            style = LocalTextStyle.current.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            ),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            if (item.reply != null) {

                item.reply?.forEachIndexed { idx, reply ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        ConstraintLayout(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth()
                        ) {
                            val (photoRef, contentRef) = createRefs()
                            Column(
                                modifier = Modifier.padding().constrainAs(photoRef) {
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
                                    Image(
                                        painter = painterResource(id = R.drawable.anya),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                if (idx != (item.reply!!.size -1)) {
                                    Divider(
                                        color = black4d,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(2.dp)
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.padding(start = 16.dp).padding(bottom = 14.dp).constrainAs(contentRef) {
                                    top.linkTo(parent.top)
                                    start.linkTo(photoRef.end)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }) {
                                Text(
                                    text = reply.username,
                                    fontFamily = PoppinsFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    style = LocalTextStyle.current.copy(
                                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                                    ),
                                )
                                Text(
                                    text = reply.desc,
                                    fontFamily = PoppinsFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black,
                                    style = LocalTextStyle.current.copy(
                                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                                    ),
                                )

                            }
                        }
                    }
                }

            }
            Divider(color = black4d, thickness = 1.dp)
        }
    }
}

@Composable
fun SearchForum() {
    Text(
        text = stringResource(id = R.string.forum),
        fontFamily = PoppinsFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        style = LocalTextStyle.current.copy(
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        modifier = Modifier.padding(top = 16.dp)
    )
    var search by remember { mutableStateOf("") }
    TextField(
        value = search,
        placeholder = { Text(stringResource(id = R.string.cari)) },
        onValueChange = {
            search = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp)
            .padding(horizontal = 12.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        textStyle = TextStyle(
            fontFamily = PoppinsFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
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
}

@Composable
fun TopForum() {
    Surface(
        color = primary,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        modifier = Modifier
            .height(102.dp)
            .fillMaxWidth()
    ) {

    }
}

//@Preview(showBackground = true, name = "Forum preview")
//@Composable
//fun PolaPreview() {
//    ForumScreen(navController)
//}