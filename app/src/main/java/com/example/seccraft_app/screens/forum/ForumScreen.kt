package com.example.seccraft_app.screens.forum


import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.seccraft_app.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.collection.forum.ForumCollection
import com.example.seccraft_app.collection.forum.ReplyForum
import com.example.seccraft_app.collection.user.DataUser
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.firestore.Query
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

enum class MultiFloatingState {
    Expanded,
    Collapsed
}

enum class Identifier {
    TextForum,
    ImageForum
}

class MinFabItem(
    val icon: Int,
    val label: String,
    val identifier: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(navController: NavHostController) {
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }

    val items = listOf(
        MinFabItem(
            icon = R.drawable.image,
            label = "",
            identifier = Identifier.ImageForum.name
        ),

        MinFabItem(
            icon = R.drawable.pencil__fabs,
            label = "",
            identifier = Identifier.TextForum.name
        ),
    )

    Scaffold(
        floatingActionButton = {
            MultiFloatingButton(
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items,
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .padding(it)
        ) {

            var search by remember { mutableStateOf("") }


            Column {
                TopForum()
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                    var listforum by remember { mutableStateOf(listOf<ForumCollection>()) }
                    var filteredList by remember {
                        mutableStateOf(listOf<ForumCollection>())
                    }

                    JetFirestore(
                        path = { collection("Forum") },
                        queryOnCollection = { orderBy("time", Query.Direction.DESCENDING) },
                        onRealtimeCollectionFetch = { values, exception ->
                            listforum = values.getListOfObjects()
                        },
                    ) { pagination ->

                        val lazyListState = rememberLazyListState()
                        val isScrolledToBottom = !lazyListState.canScrollForward

                        LaunchedEffect(isScrolledToBottom) {
                            if (isScrolledToBottom) pagination.loadNextPage()
                            Log.d("Apakah scroll bawah?", "GridItem: $isScrolledToBottom")
                        }

                        LazyColumn(
                            state = lazyListState,
                            contentPadding = PaddingValues(top = 48.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (search == "") {
                                items(listforum) { forum ->
                                    CardItemForum(forum, navController)
                                }
                            } else {
                                filteredList = listforum.filter {
                                    it.TextForum.contains(
                                        search,
                                        ignoreCase = true
                                    )
                                }
                                items(filteredList) { forum ->
                                    CardItemForum(forum, navController)
                                }

                            }

                        }
                    }

                }
            }

            TextField(
                value = search,
                placeholder = { Text(stringResource(id = R.string.cari)) },
                onValueChange = {
                    search = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 72.dp)
                    .padding(horizontal = 32.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                textStyle = MaterialTheme.typography.labelMedium,
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
    }


}

@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    navController: NavHostController,
    items: List<MinFabItem>
) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")

    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == MultiFloatingState.Expanded) 1f else 0f
    }


    Column(
        horizontalAlignment = Alignment.End,
    ) {
        if (transition.currentState == MultiFloatingState.Expanded) {
            items.forEach {
                MinFab(
                    item = it,
                    onMinFabItemClick = { minFabItem ->
                        when (minFabItem.identifier) {
                            Identifier.TextForum.name -> {
                                navController.navigate(Screens.TextForum.route)
                            }
                            Identifier.ImageForum.name -> {
                                navController.navigate(Screens.ImageForum.route)
                            }
                        }
                    },
                    alpha = alpha,
                )
            }

        }
        FloatingActionButton(
            onClick = {
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.Expanded) {
                        MultiFloatingState.Collapsed
                    } else {
                        MultiFloatingState.Expanded
                    }
                )
            },
            shape = CircleShape,
            modifier = Modifier.padding(top = 14.dp),
            containerColor = secondary

        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.rotate(rotate)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinFab(
    item: MinFabItem,
    alpha: Float,
    showLabel: Boolean = true,
    onMinFabItemClick: (MinFabItem) -> Unit
) {

    Row(modifier = Modifier.padding(top = 14.dp)) {
        if (showLabel) {
            Text(
                text = item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .alpha(
                        animateFloatAsState(targetValue = alpha, animationSpec = tween(50)).value
                    )
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
                    .background(Color.Transparent)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(cream),
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    onClick = {
                        onMinFabItemClick.invoke(item)
                    },

                    )
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = "",
                modifier = Modifier.padding(10.dp),
                tint = Color.White
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowImage(
    image: String,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Card(modifier = Modifier.size(350.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemForum(item: ForumCollection, navController: NavHostController) {

    var user by remember { mutableStateOf(DataUser()) }
    var replyForum by remember { mutableStateOf(listOf<ReplyForum>()) }
    var showReply by remember { mutableStateOf(false) }
    var countReply by remember { mutableStateOf(0) }
    val showDialog = remember { mutableStateOf(false) }


    if (showDialog.value) {
        ShowImage(image = item.image) {
            showDialog.value = it
        }
    }

    JetFirestore(
        path = { document("users/${item.idUser}") },
        onSingleTimeDocumentFetch = { value, exception ->
            val email = value!!.getString("email").toString()
            val name = value.getString("name").toString()
            val number = value.getString("number").toString()
            val image = value.getString("image").toString()

            user = user.copy(image, name, email, number)
        }
    ) {
        JetFirestore(
            path = { collection("Forum/${item.id}/ReplyForum") },
            queryOnCollection = { orderBy("time", Query.Direction.ASCENDING) },
            onSingleTimeCollectionFetch = { values, exception ->
                replyForum = values.getListOfObjects()
            },
        ) {

            countReply = replyForum.count()

            Card(
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                modifier = Modifier.clickable {
                    navController.navigate("forum_detail_screen/${item.id}")
                }
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
                            if (replyForum.isNotEmpty() && showReply) {
                                Divider(
                                    color = black4d,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(2.dp)
                                )
                            }
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
                                text = item.TextForum,
                                fontFamily = PoppinsFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                style = LocalTextStyle.current.copy(
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
                            )
                            if (item.image != "") {
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
                                        painter = rememberAsyncImagePainter(model = item.image),
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

                                Row(modifier = Modifier.clickable { navController.navigate("forum_reply_screen/${item.id}/null") }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.chat),
                                        contentDescription = "",
                                    )
                                }

                                if (!showReply && countReply != 0) {
                                    Text(
                                        text = countReply.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.lihat_balas),
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = gray_25,
                                        style = LocalTextStyle.current.copy(
                                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                                        ),
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clickable {
                                                showReply = true
                                            }
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.balas),
                                        fontFamily = PoppinsFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = gray_25,
                                        style = LocalTextStyle.current.copy(
                                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                                        ),
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clickable {
                                                navController.navigate("forum_reply_screen/${item.id}/null")
                                            }
                                    )
                                }

                            }
                        }
                    }

                    if (replyForum.isNotEmpty() && showReply) {

                        replyForum.forEachIndexed { idx, reply ->

                            val showReplyDialog = remember { mutableStateOf(false) }

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

                                            userReply = userReply.copy(image, name, email, number)
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
                                                        painter = rememberAsyncImagePainter(model = userReply.image),
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
                                                buildAnnotatedString {

                                                    withStyle(style = SpanStyle(color = Color.Blue)) {
                                                        append(reply.TextReply.substringBefore('\n'))
                                                    }

                                                    append('\n'+reply.TextReply.substringAfter('\n'))
                                                },
                                                style = MaterialTheme.typography.labelMedium

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
                                                        navController.navigate("forum_reply_screen/${item.id}/${reply.id}")
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

                                            if (idx == (replyForum.size - 1)) {
                                                Text(
                                                    text = stringResource(id = R.string.sembunyikan_balas),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color.Black,
                                                    modifier = Modifier
                                                        .padding(top = 24.dp)
                                                        .clickable {
                                                            showReply = false
                                                        }
                                                )
                                            }

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

@Composable
fun TopForum() {
    Surface(
        color = primary,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        modifier = Modifier
            .height(102.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(id = R.string.forum),
            fontFamily = PoppinsFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.padding(top = 16.dp, start = 20.dp)
        )

    }
}

//@Preview(showBackground = true, name = "Forum preview")
//@Composable
//fun ForumScreenPreview() {
//    ForumScreen()
//}