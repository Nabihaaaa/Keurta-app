package com.example.seccraft_app.screens.artikel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.ui.theme.bg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailArtikelScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Surface(modifier = Modifier.fillMaxSize(), color = bg) {
                Box {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(410.dp)) {

                        //Image(painter = rememberAsyncImagePainter(model = ), contentDescription = )

                    }

                }
            }
        }
    }


}