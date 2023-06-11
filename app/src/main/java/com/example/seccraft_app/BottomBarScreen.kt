package com.example.seccraft_app

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : Int
){
    object Beranda:BottomBarScreen(
        route = "Beranda",
        title = "Beranda",
        icon = R.drawable.icon__home
    )
    object Forum:BottomBarScreen(
        route = "Forum",
        title = "Forum",
        icon = R.drawable.icon__people
    )
    object Aktivitas:BottomBarScreen(
        route = "Aktivitas",
        title = "Aktivitas",
        icon = R.drawable.icon_book
    )
    object Profil:BottomBarScreen(
        route = "Profil",
        title = "Profil",
        icon = R.drawable.icon_profile
    )
}
