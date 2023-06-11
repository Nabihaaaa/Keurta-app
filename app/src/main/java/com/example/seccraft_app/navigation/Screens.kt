package com.example.seccraft_app.navigation

sealed class Screens(val route: String){
    object Point : Screens("point_screen")
    object Pola : Screens("pola_screen")
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Splash:Screens("splash_screen")
    object MyKursus: Screens("mykursus_screen")
    object EditProfile : Screens("editprofile_screen")
}