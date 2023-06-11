package com.example.seccraft_app.screens.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.Kursus.MyKursusCollection
import com.example.seccraft_app.Collection.Kursus.MyKursusItem
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*


@Composable
fun MyKursus(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopMyKursus(navController)
        ItemMyKursus()
    }
}

@Composable
fun ItemMyKursus() {

    val item1 = MyKursusItem("0", "Persiapan Bahan", "bahan-bahan")
    val item2 = MyKursusItem("1", "Pemotongan Pola", "pola-pola")
    val kursus = MyKursusCollection("0", "Menjahit - Cardingan", mutableListOf(item1, item2))

    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        item() {
            CardItemMyKursus(
                title = kursus.title,
                list = kursus.list
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemMyKursus(title: String, list: MutableList<MyKursusItem>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            modifier = Modifier.padding(start = 25.dp)
        ) {
            Text(
                text = title,
                fontFamily = PoppinsFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = secondary,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            )
        }
        Divider(color = gray_DAA, thickness = 1.dp)
        for (item in list) {
            var expanded by remember { mutableStateOf(false) }
            Card(
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clickable { expanded = !expanded }
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 14.dp)
                    ) {
                        Text(
                            text = item.title,
                            fontFamily = PoppinsFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            style = LocalTextStyle.current.copy(
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            ),
                        )
                        if (expanded) {
                            Text(
                                text = item.desc.repeat(20),
                                fontFamily = PoppinsFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                style = LocalTextStyle.current.copy(
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
                                modifier = Modifier.padding(top = 14.dp, end = 14.dp)
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .graphicsLayer {
                                if (expanded) {
                                    this.rotationZ = 180f
                                }
                            }
                    )
                }
                Divider(color = gray_DAA, thickness = 1.dp)
            }
        }

    }

}

@Composable
fun TopMyKursus(navController: NavHostController) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        color = primary,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 28.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "",
                tint = icon_faded,
                modifier = Modifier
                    .padding(end = 22.dp)
                    .clickable {
                        navController.navigate(BottomBarScreen.Profil.route)
                    }
            )
            Text(
                text = stringResource(id = R.string.kursus),
                fontFamily = PoppinsFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = icon_faded,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


