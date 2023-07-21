package com.example.seccraft_app.screens.kursus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.R
import com.example.seccraft_app.collection.kursus.KursusViewModelFactory
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontentKursusScreen(
    navController: NavHostController,
    idKursus: String?,
    kontenKursusModel: KontenKursusModel = viewModel(
        factory = KursusViewModelFactory(
            idKursus!!
        )
    )
) {

    val dataKursus = kontenKursusModel.dataKursus

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {

        Column {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(primary),
                shape = RectangleShape
            ) {
                Row(
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp, bottom = 28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                navController.navigate(BottomBarScreen.Kursus.route)
                            }
                    )
                    Text(
                        text = dataKursus.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(233.dp)
            ) {

            }

        }

    }

}

