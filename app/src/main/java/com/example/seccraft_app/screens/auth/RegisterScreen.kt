package com.example.seccraft_app.screens.auth

import android.content.ContentValues.TAG
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.seccraft_app.screens.util.Accompanist
import com.example.seccraft_app.collection.User.DataUser
import com.example.seccraft_app.navigation.Screens
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private var email: String = ""
private var password: String = ""
private var name: String = ""
private var number: String = ""


@Composable
fun RegisterScreen(navController: NavHostController) {
    Accompanist().TopBar(color = primary)
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = primary),
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
                        TextDaftar()
                        InputNama()
                        InputEmailRegister()
                        InputPasswordRegister()
                        InputNomor()
                        TextSyarat()
                        BtnDaftar(navController)
                        BelumAkun(navController)
                    }
                }

            }
        }
    }
}

private fun RegisterAkun(
    email: String,
    password: String,
    name: String,
    number: String,
    navController: NavHostController,
    context: Context,
) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")

                val dataUser = DataUser("",name, email, number, "user")
                val currentUser = auth.currentUser

                db.collection("users").document(currentUser?.uid!!)
                    .set(dataUser)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context, R.string.register_sc, Toast.LENGTH_LONG).show()
                        navController.navigate(Screens.Login.route)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, R.string.register_fail, Toast.LENGTH_LONG).show()
                    }


            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                Toast.makeText(
//                    baseContext,
//                    "Authentication failed.",
//                    Toast.LENGTH_SHORT,
//                ).show()
//                updateUI(null)
            }
        }

}

@Composable
fun InputEmailRegister() {
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
fun InputPasswordRegister() {
    var open by remember {
        mutableStateOf(false)
    }
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

        val trailingIconView = @Composable {
            IconButton(
                onClick = {
                    open = !open
                },
            ) {
                if (open) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_open),
                        contentDescription = "",
                        tint = icon_faded
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_close),
                        contentDescription = "",
                        tint = icon_faded
                    )
                }
            }
        }
        var inp_password by rememberSaveable { mutableStateOf("") }
        val errorMessage = "Password minimal 6 karakter"
        var isError by rememberSaveable { mutableStateOf(false) }
        val charMin = 6

        fun validate(text: String) {
            isError = inp_password.length < charMin
        }

        OutlinedTextField(
            value = inp_password,
            onValueChange = {
                inp_password = it
                validate(inp_password)
            },
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            isError = isError,
            visualTransformation = if (open) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth(),

            trailingIcon = trailingIconView
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontFamily = PoppinsFamily,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        password = inp_password
    }


}

@Composable
fun BelumAkun(navController: NavHostController) {
    Row(
        modifier = Modifier
            .padding(top = 48.dp, bottom = 100.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.sudah_akun),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = gray_88
        )
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.masuk)),
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = gray_88
            ),
            onClick = {
                navController.navigate(Screens.Login.route)
            }
        )


    }
}

@Composable
fun BtnDaftar(navController: NavHostController) {
    val context = LocalContext.current
    Button(
        onClick = {
            RegisterAkun(email, password, name, number, navController, context)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(secondary)
    ) {
        Text(
            text = stringResource(id = R.string.daftar),
            fontFamily = PoppinsFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
fun TextSyarat() {
    Text(
        text = stringResource(id = R.string.syarat),
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = gray_88,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 16.dp)
            .padding(horizontal = 24.dp)
    )
}

@Composable
fun InputNomor() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.telp),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_num by remember { mutableStateOf("") }
        OutlinedTextField(
            value = inp_num,
            onValueChange = { inp_num = it },
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )

        number = inp_num
    }
}

@Composable
fun InputNama() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.nama),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = inp_name,
            onValueChange = { inp_name = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )

        name = inp_name
    }
}

@Composable
fun TextDaftar() {
    Text(
        text = stringResource(id = R.string.daftar),
        fontFamily = PoppinsFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = green_32
    )
    Text(
        text = stringResource(id = R.string.daftar_desc),
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = gray_88,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 48.dp)
    )
}
