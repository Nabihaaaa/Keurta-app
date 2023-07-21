package com.example.seccraft_app.googleSignIn

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError : String? = null
)
