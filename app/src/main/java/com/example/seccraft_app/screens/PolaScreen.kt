package com.example.seccraft_app.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*

@Composable
fun PolaScreen(navController: NavHostController) {
    Column() {
        TopPola(navController)
        PolaItem()
    }
}

@Composable
fun PolaItem() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top=24.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        items(500) { index ->
            CardItem(
                image = painterResource(id = R.drawable.aneta_pawlik_1q7ndrpgmts_unsplash),
                title = "Pola Sulam Pemandangan",
                type = listOf("Sulam", "Pemandangan"),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(image: Painter, title: String, type: List<String>) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier.wrapContentHeight(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Card(colors = CardDefaults.cardColors(Color.White), modifier = Modifier.fillMaxWidth().height(130.dp)) {
                Image(painter = image, contentDescription = "", modifier = Modifier.fillMaxSize())
            }
            Text(
                text = title,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                for (name in type) {
                    Card(
                        colors = CardDefaults.cardColors(tertiary),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = name,
                            fontFamily = PoppinsFamily,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            style = LocalTextStyle.current.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }
        }

    }

}




@Composable
fun TopPola(navController: NavHostController) {
    Row(
        Modifier
            .background(primary)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "",
            tint = icon_faded,
            modifier = Modifier
                .weight(0.1f)
                .clickable {
                    navController.navigate(BottomBarScreen.Beranda.route)

                }
        )
        var search by remember { mutableStateOf("") }
        TextField(
            value = search,
            placeholder = { Text(stringResource(id = R.string.cari_pola)) },
            onValueChange = {
                search = it
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .weight(1f),
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

        Icon(
            painter = painterResource(id = R.drawable.information),
            contentDescription = "",
            tint = icon_faded,
            modifier = Modifier.weight(0.1f)
        )

    }
}

//@Preview(showBackground = true, name = "Pola preview")
//@Composable
//fun PolaPreview() {
//    SeccraftappTheme {
//        Pola()
//    }
//}
