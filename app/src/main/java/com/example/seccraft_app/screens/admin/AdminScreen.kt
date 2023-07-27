package com.example.seccraft_app.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.user.DataRegistrasiPaguyuban
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavHostController, dataAdminModel: DataAdminModel = viewModel()) {
    Accompanist().TopBar(color = primary)
    var search by remember { mutableStateOf("") }

    val listPaguyuban = dataAdminModel.listPagyuban

    var filteredList by remember {
        mutableStateOf(listOf<DataRegistrasiPaguyuban>())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)

    ) {
        Column {
            TopAdmin(navController)
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 48.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (search == "") {
                        items(listPaguyuban) { forum ->
                            CardPaguyuban(forum, navController)
                        }
                    } else {
                        filteredList = listPaguyuban.filter {
                            it.name.contains(
                                search,
                                ignoreCase = true
                            )
                        }
                        items(filteredList) { forum ->
                            CardPaguyuban(forum, navController)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPaguyuban(paguyuban: DataRegistrasiPaguyuban, navController: NavHostController) {
    Card(
        modifier = Modifier
            .height(130.dp)
            .padding(top = 12.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("detail_paguyuban_screen/${paguyuban.id}")
            },
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Row {
            Card(modifier = Modifier.size(130.dp), shape = RoundedCornerShape(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = paguyuban.image),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.padding(start = 14.dp)) {
                Text(
                    text = paguyuban.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Text(
                    text = paguyuban.deskripsi,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

}

@Composable
fun TopAdmin(navController: NavHostController) {
    Surface(
        color = primary,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        modifier = Modifier
            .height(102.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(start = 20.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    navController.navigate(BottomBarScreen.Profil.route)
                }
            )

            Text(
                text = stringResource(id = R.string.admin),
                fontFamily = PoppinsFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                modifier = Modifier.padding(start = 26.dp)
            )

        }



    }
}

