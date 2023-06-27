package com.example.seccraft_app.screens.kursus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*

@Composable
fun Kursus() {
    Surface(color = bg) {
        Column() {
            TopKursus()
            itemKursus()
        }
    }

}

@Composable
fun TopKursus() {
    Row(
        Modifier
            .background(primary)
            .fillMaxWidth()
            .padding(20.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "",
            tint = icon_faded,
            modifier = Modifier
                .weight(0.1f)
//                .clickable {
//                    navController.navigate(Screens.Home.route)
//                }
        )
        Text(
            text = stringResource(id = R.string.kursus_daring),
            fontFamily = PoppinsFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = icon_faded,
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(id = R.drawable.information),
            contentDescription = "",
            tint = icon_faded,
            modifier = Modifier.weight(0.1f)
        )

    }
}

@Composable
fun itemKursus() {
    LazyColumn(
        contentPadding = PaddingValues(top=24.dp, start = 20.dp, end = 20.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        items(50) { index ->
            CardItemKursus(
                title = "Paket 1",
                desc = "Membuat Anyaman dengan Bambu dan Rotan",
                harga = 120000
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemKursus(title: String, desc:String, harga:Int) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier.wrapContentHeight().shadow(4.dp, shape = RoundedCornerShape(10.dp)),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            Text(
                text = title,
                fontFamily = PoppinsFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row (modifier = Modifier.fillMaxSize()){
                Text(
                    text = desc,
                    fontFamily = PoppinsFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier.padding().weight(1f)
                )
                Text(
                    text = "Rp$harga",
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier.padding().weight(1f),
                    textAlign = TextAlign.End,

                )
            }
        }
    }

    Divider(color = gray_DAA, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
}

@Preview(showBackground = true, name = "Pola preview")
@Composable
fun KursusPreview() {
    Kursus()
}
