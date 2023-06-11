package com.example.seccraft_app.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private var email: String = ""
private var password: String = ""

@Composable
fun LoginScreen(navController: NavHostController) {
    LazyColumn() {
        item() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = bg),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(120.dp)
                        .height(80.dp)
                )
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                    shadowElevation = 30.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                            .padding(top = 32.dp)
                    ) {
                        TextMasuk()
                        InputEmail()
                        InputPassword()
                        LupaSandi()
                        BtnMasuk(navController)
                        BtnGoogle()
                        TextAkun(navController)
                    }
                }

            }
        }
    }
}


private fun Login(
    email: String,
    password: String,
    navController: NavHostController,
    context: Context
) {
    auth = Firebase.auth
    if (email=="" && password==""){
        Toast.makeText(context, R.string.empty, Toast.LENGTH_LONG).show()
    }else{
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, R.string.login_sc, Toast.LENGTH_LONG).show()
                    navController.navigate(BottomBarScreen.Beranda.route)
                } else {
                    Toast.makeText(context, R.string.login_fail, Toast.LENGTH_LONG).show()
                }
            }
    }
}


@Composable
fun TextAkun(navController: NavHostController) {
    Row(
        modifier = Modifier
            .padding(top = 48.dp, bottom = 48.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.belum_akun),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = gray_88
        )
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.daftar)),
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = gray_88
            ),
            onClick = {
                navController.navigate(Screens.Register.route)
            }
        )


    }

}

@Composable
fun BtnGoogle() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(gray_DA)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "google",
            )

            Text(
                text = stringResource(id = R.string.google),
                fontFamily = PoppinsFamily,
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TextMasuk() {
    Text(
        text = stringResource(id = R.string.masuk),
        fontFamily = PoppinsFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = green_32
    )
    Text(
        text = stringResource(id = R.string.masuk_desc),
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = gray_88,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BtnMasuk(navController: NavHostController) {
    val context = LocalContext.current
    Button(
        onClick = {
            //navController.navigate(BottomBarScreen.Beranda.route)
            Login(email, password, navController, context)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .height(50.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(secondary)
    ) {
        Text(
            text = stringResource(id = R.string.masuk),
            fontFamily = PoppinsFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }

    Text(
        text = stringResource(id = R.string.atau),
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = gray_88,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}

@Composable
fun InputEmail() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.email),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_email by remember { mutableStateOf("") }
        OutlinedTextField(
            value = inp_email,
            onValueChange = { inp_email = it },
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )

        email = inp_email
    }

}

@Composable
fun InputPassword() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.sandi),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_password by rememberSaveable { mutableStateOf("") }
        val trailingIconView = @Composable {
            IconButton(
                onClick = {

                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.eye_close),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
        OutlinedTextField(
            value = inp_password,
            onValueChange = { inp_password = it },
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth(),

            trailingIcon = trailingIconView
        )
        password = inp_password
    }


}

@Composable
fun LupaSandi() {
    ClickableText(
        text = AnnotatedString(stringResource(id = R.string.lupa_sandi)),
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = gray_88,
            textAlign = TextAlign.End,
        ),
        onClick = { offset ->
            Log.d("ClickableText", "$offset -th character is clicked.")
        }
    )
}

//@Preview(showBackground = true, name = "Home preview")
//@Composable
//fun login() {
//    ThreadTalesappTheme {
//
//        LoginScreen()
//    }
//}

