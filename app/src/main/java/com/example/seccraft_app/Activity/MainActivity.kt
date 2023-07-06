package com.example.seccraft_app.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.seccraft_app.MainScreen
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme(typography = Poppins) {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }

        }


    }


}





