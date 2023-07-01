package com.example.seccraft_app.screens.aktivitas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.ui.theme.Poppins
import com.example.seccraft_app.ui.theme.primary
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.bg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AktivitasScreen() {

    Accompanist().TopBar(color = primary)

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(primary),
                shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.aktivitas),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, start = 20.dp, bottom = 64.dp)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 84.dp)
                        .height(86.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 10.dp, bottom = 16.dp)
                        ) {

                            Text(
                                text = stringResource(id = R.string.riwayat),
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(end = 18.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.suka),
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(end = 18.dp)
                            )

                        }

                    }
                }
            }

        }


    }

}

@Composable
@Preview
fun AktivitasScreenPrv() {

    MaterialTheme(typography = Poppins) {
        AktivitasScreen()
    }

}