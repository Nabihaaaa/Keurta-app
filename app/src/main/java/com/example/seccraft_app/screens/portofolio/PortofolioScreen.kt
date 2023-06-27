package com.example.seccraft_app.screens.portofolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortofolioScreen(navController: NavHostController) {
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

            //item Pola
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 12.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
            ) {
                items(6) { index ->
                    CardItem(
                        image = painterResource(id = R.drawable.image_rotan),
                        title = "Tas Rotan",
                        name = "Annisa",
                    )
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(image: Painter, title: String, name: String) {
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
                    .height(130.dp)
            ) {
                Image(painter = image, contentDescription = "", modifier = Modifier.fillMaxSize())
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.love),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "5",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }


            }


        }

    }

}

//@Preview(showBackground = true, name = "Pola preview")
//@Composable
//fun PolaPreview() {
//    SeccraftappTheme {
//        Pola()
//    }
//}
