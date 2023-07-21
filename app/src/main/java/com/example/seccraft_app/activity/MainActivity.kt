package com.example.seccraft_app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.example.seccraft_app.MainScreen
import com.example.seccraft_app.googleSignIn.GoogleAuthUiClient
import com.example.seccraft_app.googleSignIn.LocalGoogleAuthUiClient
import com.example.seccraft_app.ui.theme.*
import com.google.android.gms.auth.api.identity.Identity


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme(typography = Poppins) {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(
                    LocalGoogleAuthUiClient provides googleAuthUiClient
                ) {
                    MainScreen()
                }
            }

        }


    }


}





