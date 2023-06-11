package com.example.seccraft_app.Collection.User

data class DataUser(
    var image: String = "",
    var name: String = "",
    var email: String = "",
    var number: String = ""
){
    constructor(): this ("","","","")
}
