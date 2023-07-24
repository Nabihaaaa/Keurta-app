package com.example.seccraft_app.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.googleSignIn.SignInViewModel
import com.example.seccraft_app.googleSignIn.LocalGoogleAuthUiClient
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
import com.example.seccraft_app.screens.kursus.KontentKursusScreen
import com.example.seccraft_app.screens.kursus.KursusScreen
import com.example.seccraft_app.screens.kursus.PembayaranScreen
import com.example.seccraft_app.screens.kursus.add.AddKursusScreen
import com.example.seccraft_app.screens.kursus.add.KontenScreen
import com.example.seccraft_app.screens.kursus.add.halaman.EditHalamanUtamaScreen
import com.example.seccraft_app.screens.portofolio.AddPortofolioScreen
import com.example.seccraft_app.screens.portofolio.DetailPortofolioScreen
import com.example.seccraft_app.screens.profile.EditProfileScreen
import com.example.seccraft_app.screens.profile.PointScreen
import com.example.seccraft_app.screens.profile.ProfileScreen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController
){
    val googleAuthUiClient = LocalGoogleAuthUiClient.current
    val lifecycleScope = rememberCoroutineScope()
    NavHost(modifier = modifier  ,navController = navController, startDestination = Screens.Splash.route){
        composable(Screens.Login.route){
            val viewModel = viewModel<SignInViewModel>()
            val context = LocalContext.current
            val state by viewModel.state.collectAsStateWithLifecycle()
            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(), onResult = {result->
                if (result.resultCode == RESULT_OK){
                    lifecycleScope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        viewModel.onSignInResult(signInResult)
                    }
                }
            })

            LaunchedEffect(key1 = state.isSignInSuccessful ){
                if (state.isSignInSuccessful){

                    val user = googleAuthUiClient.getSignedInUser()
                    val db = Firebase.firestore

                    if (user != null) {
                        val userDocRef = db.collection("users").document(user.id)

                        userDocRef.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    Toast.makeText(context, R.string.login_sc, Toast.LENGTH_LONG).show()
                                    navController.navigate(BottomBarScreen.Beranda.route)
                                } else {
                                    userDocRef.set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, R.string.register_sc, Toast.LENGTH_LONG).show()
                                            navController.navigate(BottomBarScreen.Beranda.route)
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, R.string.login_fail, Toast.LENGTH_LONG).show()
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error checking user document: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    }

                }
            }

            LoginScreen(navController = navController, state =state ) {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }
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
            ProfileScreen(navController = navController, signOutGoogle = {
                lifecycleScope.launch {
                    googleAuthUiClient.signOut()
                }

            })
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
        composable(Screens.KontenKursus.route){
            KontentKursusScreen(navController = navController, it.arguments?.getString("documentId"))
        }
        composable(Screens.AddKursus.route){
            AddKursusScreen(navController = navController)
        }
        composable(Screens.KontenScreen.route){
            KontenScreen(navController=navController, it.arguments?.getString("documentId"))
        }
        composable(Screens.EditHalamanUtamaScreen.route){
            EditHalamanUtamaScreen(navController=navController, it.arguments?.getString("documentId"))
        }
    }

}