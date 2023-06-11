package com.example.seccraft_app.Collection.User

import android.net.Uri

data class DataUser(
    var image: Uri? = null,
    var name: String = "",
    var email: String = "",
    var number: String = ""
){
    constructor(): this (null,"","","")
}
