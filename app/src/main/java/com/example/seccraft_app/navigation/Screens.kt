package com.example.seccraft_app.navigation

sealed class Screens(val route: String){
    object Point : Screens("point_screen")
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Splash:Screens("splash_screen")
    object EditProfile : Screens("editprofile_screen")
    object TextForum : Screens("forum_text_screen")
    object ImageForum : Screens("forum_image_screen")
    object ReplyForum : Screens("forum_reply_screen/{documentId}/{replyId}")
    object Portofolio : Screens("portofolio_screen")
    object AddPortofolio : Screens("portofolio_add_screen")
    object DetailPortofolio : Screens("portofolio_detail_screen/{documentId}")
    object DetailForum : Screens("forum_detail_screen/{documentId}")
    object Artikel : Screens("artikel_screen")
    object DetailArtikel : Screens("artikel_detail_screen/{documentId}")
    object AddArtikel : Screens("artikel_add_screen")
    object DetailKursus : Screens("kursus_detail_screen/{documentId}")
    object PembayaranKursus : Screens("pembayaran_kursus_screen/{documentId}")
    object KontenKursus : Screens("konten_kursus_screen/{documentId}")
    object AddKursus : Screens("add_kursus_screen")
    object KontenScreen : Screens("konten_screen/{documentId}")
    object EditHalamanUtamaScreen : Screens("edit_halaman_utama_screen/{documentId}")
    object AddKontenScreen : Screens("add_konten_screen/{documentId}/{page}")
    object EditKontenScreen : Screens("edit_konten_screen/{documentId}/{kontenId}")
    object RegistrasiPaguyuban : Screens("registrasi_paguyuban_screen")
}