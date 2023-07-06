package com.example.seccraft_app.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.primary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavController) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(primary)
)
{
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var screen = Screens.Login.route
    if (currentUser != null) {
        screen = BottomBarScreen.Beranda.route
    }
    Accompanist().TopBar(color = primary)
    val scale = remember {
        androidx.compose.animation.core.Animatable(0.0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(1000)
//This stop to appear splash screen on pressing back button
        navController.navigate(screen) {
            popUpTo(Screens.Splash.route) {
                inclusive = true
            }
        }
    }

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .scale(scale.value)
    )
}
