package com.example.seccraft_app.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*

@Composable
fun HomeScreen(navController: NavHostController) {
    Accompanist().TopBar(color = primary)
    Surface(
        color = bg
    ) {
        LazyColumn() {
            item {
                Box {
                    HomeTop()
                    Column {
                        points()
                        fiture(navController)
                        event()
                        artikel()
                    }

                }
            }

        }

    }

}

@Composable
private fun HomeTop() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Surface(
            color = primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
        ){
            Column(
                Modifier.padding(start = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    Modifier.size(100.dp)
                    )
            }

        }

    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun points() {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 100.dp)
            .height(100.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = black80,
                spotColor = black80
            )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                stringResource(id = R.string.Hai_user),
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                stringResource(id = R.string.points),
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                color = lightGray,
                textDecoration = TextDecoration.Underline
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dollar),
                    contentDescription = "dollar",
                    tint = secondary
                )
                Text(
                    text = "1000",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = secondary,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(secondary),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .defaultMinSize(minHeight = 1.dp, minWidth = 1.dp)
                        .height(22.dp)
                        .width(96.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.selengkapnya),
                        fontFamily = PoppinsFamily,
                        fontSize = 12.sp,
                        color = Color.White,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun fiture(navController: NavHostController) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 20.dp)
            .height(117.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = black40,
                spotColor = black40
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.White)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = stringResource(
                            id = R.string.kursus
                        ),
                        modifier = Modifier.padding(14.dp),
                        tint = secondary
                    )
                }
                Text(
                    text = stringResource(id = R.string.kursus),
                    fontFamily = PoppinsFamily,
                    color = secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp),
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.White)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = stringResource(
                            id = R.string.video
                        ),
                        modifier = Modifier.padding(14.dp),
                        tint = secondary
                    )
                }
                Text(
                    text = stringResource(id = R.string.video),
                    fontFamily = PoppinsFamily,
                    color = secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp),
                    fontWeight = FontWeight.Bold
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f).clickable {
                    navController.navigate(BottomBarScreen.Portofolio.route)
                }
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.White)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pencil),
                        contentDescription = stringResource(
                            id = R.string.pola
                        ),
                        modifier = Modifier.padding(14.dp),
                        tint = secondary
                    )
                }
                Text(
                    text = stringResource(id = R.string.pola),
                    fontFamily = PoppinsFamily,
                    color = secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp),
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}

@Composable
private fun event() {
    Surface(
        modifier = Modifier
            .padding(top = 15.8.dp)
            .fillMaxWidth()
            .height(167.dp)
    ) {
        Text(text = "Gambar Event")
    }
}

@Composable
private fun artikel(
    modifier: Modifier = Modifier,
    names: List<String> = List(5) { "$it" }
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text(text = "Artikel")
        for (name in names) {
            Text(text = name)
        }
    }

}


//@Preview(showBackground = true, name = "Home preview")
//@Composable
//fun DefaultPreview() {
//    SeccraftappTheme {
//        HomeScreen(navController)
//    }
//}

