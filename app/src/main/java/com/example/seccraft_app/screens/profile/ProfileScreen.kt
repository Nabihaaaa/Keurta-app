package com.example.seccraft_app.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(
    navController: NavHostController,
    dataProfileModel: DataProfileModel = viewModel()
) {
    val getData = dataProfileModel.state.value

    Accompanist().TopBar(color = primary)
    Surface(
        color = bg
    ) {
        LazyColumn() {
            item {
                Box {
                    Top()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        UserData(navController, getData)
                        Fiture(navController)
                        Akun(navController)
                        InfoLanjutan()
                    }

                }
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoLanjutan() {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.info_lanjutan),
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = black_25,
                modifier = Modifier.padding(),
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 12.dp, bottom = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.privacy), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.kebijakan),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }
            Divider(color = gray_DAA, thickness = 1.dp)

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.draft), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.layanan),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }
            Divider(color = gray_DAA, thickness = 1.dp)

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.information),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.tentang),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Akun(navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.akun),
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = black_25,
                modifier = Modifier.padding()
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 12.dp, bottom = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.clock), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.aktivitas),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }
            Divider(color = gray_DAA, thickness = 1.dp)

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.help), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.bantuan),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }
            Divider(color = gray_DAA, thickness = 1.dp)

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.bell), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.notifikasi),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.toggle_off),
                    contentDescription = ""
                )
            }
            Divider(color = gray_DAA, thickness = 1.dp)

            Row(
                Modifier
                    .clickable {
                        navController.navigate(Screens.Login.route)
                        Firebase.auth.signOut()
                    }
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.out), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.keluar),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = black_25,
                    modifier = Modifier.padding(start = 4.dp),
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
            }

        }


    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Fiture(
    navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(top = 14.dp)
            .wrapContentHeight()
            .fillMaxWidth()


    ) {
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Image(painter = painterResource(id = R.drawable.bag), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.order),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Image(painter = painterResource(id = R.drawable.love), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.favorit),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        //navController.navigate(Screens.MyKursus.route)
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.sew), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.kursus),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screens.Point.route)
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.coin), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.poin),
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserData(navController: NavHostController, user: DataUser) {
    Text(
        text = stringResource(id = R.string.profil),
        fontFamily = PoppinsFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 30.dp)
            .height(86.dp)
    ) {
        if (user.image == "") {
            Card(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape), colors = CardDefaults.cardColors(
                    gray_DA
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_profile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        } else {
            Card(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape), colors = CardDefaults.cardColors(
                    gray_DA
                )
            ) {
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

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight()
        ) {

            Text(
                text = user.name,
                fontFamily = PoppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )

            Text(
                text = user.number,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
            )

            Text(
                text = user.email,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.pencil),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp)
                .clickable { navController.navigate(Screens.EditProfile.route) },
            tint = Color(0x99000000)
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
            .height(234.dp)
    ) {

    }
}


//@Preview(showBackground = true, name = "profile preview")
//@Composable
//fun profile() {
//    SeccraftappTheme {
//        ProfileScreen()
//    }
//}

