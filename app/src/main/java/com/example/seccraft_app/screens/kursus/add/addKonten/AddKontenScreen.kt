package com.example.seccraft_app.screens.kursus.add.addKonten

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.seccraft_app.R
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.screens.util.VideoPlayer
import com.example.seccraft_app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddKontenScreen(
    navController: NavHostController,
    idKursus: String,
    page: String,
    addKontenModel: AddKontenModel = viewModel()
) {
    Accompanist().TopBar(color = primary)
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    var title by remember {
        mutableStateOf("")
    }
    var deskripsi by remember {
        mutableStateOf("")
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = { BottomSheet { fileUri = it } },
        sheetState = sheetState
    ) {
        Scaffold(
            bottomBar = {
                BottomBarAddKontenScreen(
                    fileUri,
                    title,
                    deskripsi,
                    context,
                    navController,
                    addKontenModel,
                    idKursus,
                    page
                )
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), color = bg
            ) {
                LazyColumn {
                    item {
                        Column {
                            //top
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                colors = CardDefaults.cardColors(primary),
                                shape = RectangleShape
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        top = 24.dp,
                                        start = 20.dp,
                                        bottom = 16.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_left),
                                        contentDescription = "",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clickable {
                                                navController.popBackStack()
                                            }
                                    )
                                    Text(
                                        text = stringResource(id = R.string.kursus),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 20.dp),
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.upload_mtr_krs),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 24.dp)
                                )


                                //image
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch {
                                                if (sheetState.isVisible) sheetState.hide()
                                                else sheetState.show()
                                            }
                                        }
                                        .height(174.dp)
                                        .fillMaxWidth(),
                                    colors = if (fileUri == null) CardDefaults.cardColors(
                                        Color(
                                            0xA3B9B9B9
                                        )
                                    ) else CardDefaults.cardColors(
                                        Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(size = 14.dp)
                                ) {
                                    if (fileUri != null) {
                                        val mineType = context.contentResolver.getType(fileUri!!)
                                        if (mineType!!.startsWith("video")) {
                                            Column {
                                                Row(modifier = Modifier.fillMaxWidth()) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.pencil),
                                                        contentDescription = "",
                                                        tint = Color.Blue,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Text(
                                                        text = stringResource(id = R.string.edit),
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Color.Blue
                                                    )
                                                }
                                                Card(modifier = Modifier.fillMaxSize()) {
                                                    VideoPlayer(
                                                        modifier = Modifier.fillMaxSize(),
                                                        uri = fileUri.toString()
                                                    )
                                                }
                                            }

                                        } else {
                                            val jsonString: String = fileUri?.let { uri ->
                                                context.contentResolver.openInputStream(uri)
                                                    ?.use { inputStream ->
                                                        val byteArray = inputStream.readBytes()
                                                        byteArray.decodeToString()
                                                    }
                                            } ?: ""
                                            val composition by rememberLottieComposition(
                                                spec = LottieCompositionSpec.JsonString(
                                                    jsonString
                                                )
                                            )
                                            LottieAnimation(
                                                composition = composition,
                                                iterations = LottieConstants.IterateForever,
                                                modifier = Modifier.fillMaxSize(),
                                                alignment = Alignment.Center
                                            )
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            Icon(
                                                painter = painterResource(id = R.drawable.icn_video),
                                                contentDescription = "",
                                                tint = Color(0x9C3D3D3D),
                                                modifier = Modifier.size(56.dp)
                                            )

                                            Text(
                                                text = stringResource(id = R.string.upl_video_animasi),
                                                style = MaterialTheme.typography.headlineLarge,
                                                color = Color(0x9C3D3D3D),
                                                modifier = Modifier.padding(top = 28.dp)
                                            )
                                        }
                                    }
                                }
                                //judul
                                OutlinedTextField(
                                    value = title,
                                    placeholder = { Text(text = stringResource(id = R.string.judul)) },
                                    onValueChange = { title = it },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    textStyle = MaterialTheme.typography.labelMedium,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black,
                                        placeholderColor = icon_faded,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = primary,
                                        unfocusedBorderColor = icon_faded
                                    )
                                )
                                //deskripsi
                                OutlinedTextField(
                                    value = deskripsi,
                                    placeholder = { Text(text = stringResource(id = R.string.deskripsi)) },
                                    onValueChange = { deskripsi = it },
                                    singleLine = false,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .height(176.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    textStyle = MaterialTheme.typography.labelMedium,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black,
                                        placeholderColor = icon_faded,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = primary,
                                        unfocusedBorderColor = icon_faded
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomSheet(fileUri: (Uri?) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            fileUri(uri)
        }
    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                launcher.launch("video/*")

            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icn_video_upl),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Video Mp4",
                style = MaterialTheme.typography.displayLarge
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                launcher.launch("application/json")
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icn_anim_upload),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Animation Json",
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
fun BottomBarAddKontenScreen(
    fileUri: Uri?,
    title: String,
    deskripsi: String,
    context: Context,
    navController: NavHostController,
    addKontenModel: AddKontenModel,
    idKursus: String,
    page: String
) {

    Surface(modifier = Modifier.fillMaxWidth(), color = tertiary) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            Button(
                onClick = {
                    if (fileUri != null && title != "" && deskripsi != "") {
                        val mineType = context.contentResolver.getType(fileUri!!)
                        var video = false
                        if (mineType!!.startsWith("video")) {
                            video = true
                        }
                        addKontenModel.uploadDAta(
                            fileUri,
                            title,
                            deskripsi,
                            context,
                            navController,
                            idKursus,
                            page,
                            video
                        )
                    } else {
                        Toast.makeText(context, R.string.data_blm_lengkap, Toast.LENGTH_LONG).show()
                    }

                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    secondary
                ),
                shape = RoundedCornerShape(15.dp)
            ) {

                Text(
                    text = "Simpan",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

            }
        }
    }
}

