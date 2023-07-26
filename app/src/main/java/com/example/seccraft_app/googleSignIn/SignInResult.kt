package com.example.seccraft_app.googleSignIn

import com.example.seccraft_app.collection.user.DataUser

data class SignInResult(
    val data: DataUser?,
    val errorMessage:String?
)
