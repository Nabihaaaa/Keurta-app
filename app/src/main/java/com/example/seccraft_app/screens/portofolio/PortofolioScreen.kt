package com.example.seccraft_app.screens.portofolio

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.collection.portofolio.DataPortofolio
import com.example.seccraft_app.collection.portofolio.LikePortofolio
import com.example.seccraft_app.collection.User.UserLikePortofolio
import com.example.seccraft_app.R
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.Pagination
import com.jet.firestore.getListOfObjects
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PortofolioScreen(navController: NavHostController) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var dataPortofolio by remember { mutableStateOf(listOf<DataPortofolio>()) }
    var dataPortofolioTime by remember { mutableStateOf(listOf<DataPortofolio>()) }
    var searchText by remember { mutableStateOf("") }

    val buttonSelected = remember {
        derivedStateOf {
            pagerState.currentPage == 0
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AddPortofolio.route) },
                shape = CircleShape,
                containerColor = secondary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .padding(it)
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

                Row(
                    modifier = Modifier.padding(bottom = 28.dp, start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            navController.navigate(BottomBarScreen.Beranda.route)
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.portofolio),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }


                TextField(
                    value = searchText,
                    readOnly = false,
                    placeholder = { Text(stringResource(id = R.string.cari)) },
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                    textStyle = MaterialTheme.typography.labelMedium,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Divider(
                        modifier = Modifier
                            .height(26.dp)
                            .width(2.dp), color = Color(0xFF9A9999), thickness = 1.dp
                    )
                    Column(
                        modifier = Modifier.padding(end = 190.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.terkini),
                            style = MaterialTheme.typography.displayLarge,
                            color = if (buttonSelected.value) Color.Black else Color(0xFF9A9999),
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {
                                    val prevPageIndex = pagerState.currentPage - 1
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            prevPageIndex
                                        )
                                    }
                                },
                        )
                        if (buttonSelected.value) {
                            Divider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(110.dp), color = Color.Black, thickness = 1.dp
                            )
                        }

                    }
                    Column(
                        modifier = Modifier.padding(start = 190.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.populer),
                            style = MaterialTheme.typography.displayLarge,
                            color = if (!buttonSelected.value) Color.Black else Color(0xFF9A9999),
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {
                                    val nextPageIndex = pagerState.currentPage + 1
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            nextPageIndex
                                        )
                                    }
                                }
                        )
                        if (!buttonSelected.value) {
                            Divider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(110.dp), color = Color.Black, thickness = 1.dp
                            )
                        }

                    }
                }

                JetFirestore(
                    path = { collection("portofolio") },
                    queryOnCollection = { orderBy("time", Query.Direction.DESCENDING) },
                    onSingleTimeCollectionFetch = { values, exception ->
                        dataPortofolioTime = values.getListOfObjects()
                    },
                ) { pagination ->

                    dataPortofolio = dataPortofolioTime.sortedByDescending { it.like }

                    HorizontalPager(pageCount = 2, state = pagerState) { pageIndex ->
                        //item Pola
                        if (pageIndex == 0) {
                            GridItem(dataPortofolioTime, navController, pagination, searchText)
                        }
                        if (pageIndex == 1) {
                            GridItem(dataPortofolio, navController, pagination, searchText)
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun GridItem(
    dataPortofolio: List<DataPortofolio>,
    navController: NavHostController,
    pagination: Pagination,
    searchText: String
) {
    val lazyListState = rememberLazyGridState()
    val isScrolledToBottom = !lazyListState.canScrollForward

    var filteredList by remember {
        mutableStateOf(listOf<DataPortofolio>())
    }

    if (searchText != "") {
        filteredList = dataPortofolio.filter {

            it.judul.contains(
                searchText,
                ignoreCase = true
            ) ||
                    it.kategori!!.any { kategori ->
                        kategori.toString().contains(
                            searchText,
                            ignoreCase = true
                        )
                    }
        }

    }

    LaunchedEffect(isScrolledToBottom) {
        if (isScrolledToBottom) pagination.loadNextPage()
        Log.d("Apakah scroll bawah?", "GridItem: $isScrolledToBottom")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyListState,
        reverseLayout = false,
        contentPadding = PaddingValues(top = 12.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        items(if (searchText != "") filteredList else dataPortofolio) { portofolio ->
            var user by remember { mutableStateOf(DataUser()) }
            JetFirestore(
                path = { document("users/${portofolio.idUser}") },
                onSingleTimeDocumentFetch = { value, exception ->
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

                CardItem(
                    id = portofolio.id,
                    idUser = portofolio.idUser,
                    image = portofolio.image,
                    title = title,
                    name = name,
                    titleFull = portofolio.judul,
                    nameFull = user.name,
                    imageUser = user.image,
                    deskripsi = portofolio.deskripsi,
                    kategori = portofolio.kategori,
                    navController = navController
                )

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CardItem(
    image: String,
    title: String,
    name: String,
    idUser: String,
    id: String,
    imageUser: String,
    deskripsi: String,
    kategori: List<Any?>?,
    titleFull: String,
    nameFull: String,
    navController: NavHostController
) {

    var likeCount by remember { mutableStateOf(0) }
    var dataLike by remember { mutableStateOf(listOf<LikePortofolio>()) }
    var likeData by remember { mutableStateOf(LikePortofolio()) }

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CardItemDetail(
            titleFull,
            likeCount,
            likeData.like,
            deskripsi,
            nameFull,
            imageUser,
            kategori,
            image,
            id,
            navController
        ) {
            showDialog.value = it
        }
    }

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
            modifier = Modifier
                .wrapContentHeight()
                .combinedClickable(
                    onClick = {
                        navController.navigate("portofolio_detail_screen/$id")
                    },
                    onLongClick = {
                        showDialog.value = true
                    }
                ),
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


                    JetFirestore(
                        path = { document("portofolio/$id/like/$idCurrentUser") },
                        onRealtimeDocumentFetch = { value, exception ->

                            val idUserLike = value!!.getString("idUser").toString()
                            val like = value.getBoolean("like")

                            likeData = if (like == null) likeData.copy(
                                idUserLike,
                                false
                            ) else likeData.copy(idUserLike, like)

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
                                            LikePto(id, false, likeCount)
                                        } else {
                                            LikePto(id, true, likeCount)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemDetail(
    title: String,
    likeCount: Int,
    like: Boolean,
    deskripsi: String,
    name: String,
    imageUser: String,
    kategori: List<Any?>?,
    image: String,
    id: String,
    navController: NavHostController,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Card(
                colors = CardDefaults.cardColors(primary),
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(modifier = Modifier.padding(top = 24.dp)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.love),
                                contentDescription = "",
                                tint = if (like) Color.Red else Color.Black,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(bottom = 2.dp)
                                    .clickable {
                                        if (like) {
                                            LikePto(id, false, likeCount)
                                        } else {
                                            LikePto(id, true, likeCount)
                                        }

                                    }
                            )
                            Text(
                                text = likeCount.toString(),
                                style = MaterialTheme.typography.labelMedium
                            )

                        }
                    }

                    Text(
                        text = deskripsi,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Card(modifier = Modifier.size(32.dp), shape = CircleShape) {

                            Image(
                                painter = if (imageUser != "") rememberAsyncImagePainter(model = imageUser) else
                                    painterResource(id = R.drawable.user_profile),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize()
                            )


                        }

                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    if (kategori!!.isNotEmpty()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            kategori.forEachIndexed { index, kategori ->
                                if (kategori.toString() != "") {
                                    Card(
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.padding(
                                            top = 24.dp,
                                            start = if (index == 0) 0.dp else 12.dp
                                        ),
                                        colors = CardDefaults.cardColors(
                                            secondary
                                        )
                                    ) {
                                        Text(
                                            text = kategori.toString(),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.White,
                                            modifier = Modifier.padding(
                                                vertical = 8.dp,
                                                horizontal = 16.dp
                                            )
                                        )
                                    }
                                }

                            }

                        }
                    }

                    Text(
                        text = stringResource(id = R.string.lihat),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End,
                        color = secondary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("portofolio_detail_screen/$id")
                            }
                    )

                }
            }
        }
    }

}

fun LikePto(id: String, like: Boolean, likeCount: Int) {
    val auth = Firebase.auth
    val db = Firebase.firestore
    val idCurrentUser = auth.currentUser!!.uid

    val data = LikePortofolio(idUser = idCurrentUser, like = like)

    val dataUserPortofolio = UserLikePortofolio(idPortofolio = id, like = like)

    db.document("portofolio/$id").update("like", likeCount)

    db.collection("portofolio/$id/like/").document(idCurrentUser).set(data)
    db.collection("users/$idCurrentUser/likePortofolio/").document(id).set(dataUserPortofolio)

}

