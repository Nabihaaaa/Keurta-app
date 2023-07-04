package com.example.seccraft_app.navigation

sealed class Screens(val route: String){
    object Point : Screens("point_screen")
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Splash:Screens("splash_screen")
    object MyKursus: Screens("mykursus_screen")
    object EditProfile : Screens("editprofile_screen")
    object TextForum : Screens("forum_text_screen")
    object ImageForum : Screens("forum_image_screen")
    object ReplyForum : Screens("forum_reply_screen/{documentId}/{replyId}")
    object AddPortofolio : Screens("portofolio_add_screen")
    object DetailPortofolio : Screens("portofolio_detail_screen/{documentId}")
    object DetailForum : Screens("forum_detail_screen/{documentId}")
    object Artikel : Screens("artikel_screen")
    object DetailArtikel : Screens("artikel_detail_screen/{documentId}")
}