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
import com.example.seccraft_app.screens.auth.LoginScreen
import com.example.seccraft_app.screens.auth.RegisterScreen
import com.example.seccraft_app.screens.forum.ForumScreen
import com.example.seccraft_app.screens.forum.ImageForumScreen
import com.example.seccraft_app.screens.forum.ReplyScreen
import com.example.seccraft_app.screens.forum.TextScreen
import com.example.seccraft_app.screens.portofolio.AddPortofolioScreen
import com.example.seccraft_app.screens.profile.EditProfileScreen
import com.example.seccraft_app.screens.profile.MyKursus
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

        }
        composable(route = BottomBarScreen.Profil.route){
            ProfileScreen(navController)
        }
        composable(route = BottomBarScreen.Portofolio.route){
            PortofolioScreen(navController)
        }
        composable(Screens.Point.route){
            PointScreen(navController)
        }
        composable(Screens.MyKursus.route){
            MyKursus(navController)
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
            ReplyScreen(navController = navController, it.arguments?.getString("documentId")!!)
        }
        composable(Screens.AddPortofolio.route){
            AddPortofolioScreen(navController)
        }
    }

}