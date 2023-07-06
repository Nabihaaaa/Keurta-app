package com.example.seccraft_app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seccraft_app.ui.theme.Poppins
import com.example.seccraft_app.ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
        MainNavGraph(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController
        )
    }

}

@Composable
fun BottomBar(navController: NavHostController) {
    val context = LocalContext.current
    val screens = listOf(
        BottomBarScreen.Beranda,
        BottomBarScreen.Forum,
        BottomBarScreen.Kursus,
        BottomBarScreen.Aktivitas,
        BottomBarScreen.Profil
    )
    val routes = screens.map {
        it.route
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    LaunchedEffect(currentDestination?.route) {
//        Toast.makeText(context, currentDestination?.route.orEmpty(), Toast.LENGTH_SHORT).show()
//    }

    AnimatedVisibility(visible = routes.any { it == currentDestination?.route }) {
        NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = Color.White) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        },
        label = {
            Text(text = screen.title, style = MaterialTheme.typography.labelSmall)
        },
        icon = {
            Icon(painter = painterResource(id = screen.icon), contentDescription = "")
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color(0xFF6A9C78),
            indicatorColor = Color(0xFF6A9C78),
            unselectedIconColor = Color.Black,
            unselectedTextColor = Color.Black
        )
    )
}

