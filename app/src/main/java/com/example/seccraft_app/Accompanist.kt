package com.example.seccraft_app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class Accompanist {

    @Composable
    fun TopBar(color: androidx.compose.ui.graphics.Color){

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
    }

}