package com.example.seccraft_app.screens.artikel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.Collection.Artikel.DataArtikel
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.Poppins
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.icon_faded
import com.example.seccraft_app.ui.theme.primary
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtikelScreen(dataArtikelModel: DataArtikelModel = viewModel()) {
    val lazyListState = rememberLazyListState()
    val isScrolledToBottom = !lazyListState.canScrollForward

    val artikel = dataArtikelModel.dataArtikel
    var filteredList by remember {
        mutableStateOf(listOf<DataArtikel>())
    }

    var search by remember { mutableStateOf("") }

    if (search != "") {
        filteredList = artikel.filter {
            it.title.contains(
                search,
                ignoreCase = true
            )
        }

    }

    Accompanist().TopBar(color = primary)
    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
                colors = CardDefaults.cardColors(
                    primary
                )
            ) {



                Row(
                    modifier = Modifier.padding(
                        top = 14.dp,
                        bottom = 30.dp,
                        start = 14.dp,
                        end = 20.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp),
                        tint = icon_faded
                    )

                    TextField(
                        value = search,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.cari) + " Artikel",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        onValueChange = {
                            search = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .padding(start = 14.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                        textStyle = MaterialTheme.typography.labelSmall,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "",
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            placeholderColor = icon_faded,
                            cursorColor = Color.Black,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )

                    )

                }

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentPadding = PaddingValues(top = 36.dp)
            ) {

                items(if (search != "") filteredList else artikel) { dataArtikel ->

                    val timestamp = dataArtikel.time as com.google.firebase.Timestamp

                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    val currentTime = System.currentTimeMillis()

                    val diffInMillis = currentTime - milliseconds
                    val diffInSeconds = diffInMillis / 1000
                    val diffInMinutes = diffInSeconds / 60
                    val diffInHours = diffInMinutes / 60
                    val diffInDays = diffInHours / 24
                    val diffInWeeks = diffInDays / 7
                    val diffInMonths = diffInDays / 30
                    val diffInYears = diffInDays / 365

                    val timeAgo = when {
                        diffInYears >= 1 -> "${diffInYears.toInt()} tahun lalu"
                        diffInMonths >= 1 -> "${diffInMonths.toInt()} bulan lalu"
                        diffInWeeks >= 1 -> "${diffInWeeks.toInt()} minggu lalu"
                        diffInDays >= 1 -> "${diffInDays.toInt()} hari lalu"
                        diffInHours >= 1 -> "${diffInHours.toInt()} jam lalu"
                        diffInMinutes >= 1 -> "${diffInMinutes.toInt()} menit lalu"
                        else -> "Baru saja"
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(top = 16.dp, start = 20.dp, end = 20.dp),
                        colors = CardDefaults.cardColors(Color.Transparent),
                        shape = RectangleShape,
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(142.dp)
                                .padding(bottom = 24.dp)
                        ) {

                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.size(142.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = dataArtikel.image),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 6.dp, end = 14.dp)
                            ) {
                                Text(
                                    text = timeAgo,
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = dataArtikel.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 8.dp),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = stringResource(id = R.string.by_keurta),
                                        style = MaterialTheme.typography.labelSmall,

                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_eye),
                                        contentDescription = "",
                                        Modifier.size(22.dp)

                                    )
                                    Text(
                                        text = dataArtikel.view.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(start = 12.dp)
                                    )

                                }

                            }

                        }

                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Color(0x4D000000)
                        )

                    }
                }


            }

        }
    }
}

@Composable
@Preview
fun ArtikelScreenPrv() {

    MaterialTheme(typography = Poppins) {
        ArtikelScreen()
    }

}