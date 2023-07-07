package com.example.seccraft_app

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.screens.home.HomeScreen
import com.example.seccraft_app.screens.portofolio.PortofolioScreen
import com.example.seccraft_app.screens.SplashScreen
import com.example.seccraft_app.screens.aktivitas.AktivitasScreen
import com.example.seccraft_app.screens.artikel.AddArtikelSceen
import com.example.seccraft_app.screens.artikel.ArtikelScreen
import com.example.seccraft_app.screens.artikel.DetailArtikelScreen
import com.example.seccraft_app.screens.auth.LoginScreen
import com.example.seccraft_app.screens.auth.RegisterScreen
import com.example.seccraft_app.screens.forum.*
import com.example.seccraft_app.screens.kursus.DetailKursusScreen
import com.example.seccraft_app.screens.kursus.KursusScreen
import com.example.seccraft_app.screens.kursus.PembayaranScreen
import com.example.seccraft_app.screens.portofolio.AddPortofolioScreen
import com.example.seccraft_app.screens.portofolio.DetailPortofolioScreen
import com.example.seccraft_app.screens.profile.EditProfileScreen
import com.example.seccraft_app.screens.profile.PointScreen
import com.example.seccraft_app.screens.profile.ProfileScreen


@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController
){

    NavHost(modifier = modifier  ,navController = navController, startDestination = Screens.Splash.route){
        composable(Screens.Login.route){
            LoginScreen(navController)
        }
        composable(Screens.Register.route){
            RegisterScreen(navController)
        }
        composable(Screens.Splash.route){
            SplashScreen(navController)
        }
        composable(route = BottomBarScreen.Beranda.route){
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Forum.route){
            ForumScreen(navController)
        }
        composable(route = BottomBarScreen.Aktivitas.route){
            AktivitasScreen(navController)
        }
        composable(route = BottomBarScreen.Profil.route){
            ProfileScreen(navController)
        }
        composable(route = BottomBarScreen.Kursus.route){
            KursusScreen(navController = navController)

        }
        composable(Screens.Portofolio.route){
            PortofolioScreen(navController)
        }
        composable(Screens.Point.route){
            PointScreen(navController)
        }
        composable(Screens.EditProfile.route){
            EditProfileScreen(navController)
        }
        composable(Screens.TextForum.route){
            TextScreen(navController)
        }
        composable(Screens.ImageForum.route){
            ImageForumScreen(navController)
        }
        composable(Screens.ReplyForum.route){
            ReplyScreen(navController = navController, it.arguments?.getString("documentId")!!, it.arguments?.getString("replyId")!!)
        }
        composable(Screens.AddPortofolio.route){
            AddPortofolioScreen(navController)
        }
        composable(Screens.DetailPortofolio.route){
            DetailPortofolioScreen(navcontroller = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.DetailForum.route){
            DetailForumScreen(navController = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.Artikel.route){
            ArtikelScreen(navController = navController)
        }
        composable(Screens.DetailArtikel.route){
            DetailArtikelScreen(navController = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.DetailKursus.route){
            DetailKursusScreen(navController = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.PembayaranKursus.route){
            PembayaranScreen(navController = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.AddArtikel.route){
            AddArtikelSceen(navController=navController)
        }
    }

}