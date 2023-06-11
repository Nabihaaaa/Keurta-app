package com.example.seccraft_app.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bg
    ) {
        Box {
            Top()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            ) {
                Point(navController)
                DataPoint()
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataPoint() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(id = R.string.points),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Medium,
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
                    Modifier.size(30.dp),
                    tint = secondary
                )
                Text(
                    text = "1000",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 32.sp,
                    color = secondary,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )

            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = R.drawable.flip),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 4.dp),
                    tint = secondary
                )
                Text(
                    text = stringResource(id = R.string.setara),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = lightGray,
                )
                Text(
                    text = "10.000",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = lightGray,
                )
            }

        }
    }
}

@Composable
fun Point(navController: NavHostController) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "",
            tint = Color(0x99000000),
            modifier = Modifier.clickable { navController.navigate(BottomBarScreen.Profil.route) })
        Text(
            text = stringResource(id = R.string.poin),
            fontFamily = PoppinsFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = green_32,
            modifier = Modifier.padding(start = 24.dp),
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }

}

@Composable
private fun Top() {
    Surface(
        color = primary,
        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp)
    ) {

    }
}

//@Preview(showBackground = true, name = "Home preview")
//@Composable
//fun PointPreview() {
//    SeccraftappTheme {
//        PointScreen()
//    }
//}