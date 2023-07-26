package com.example.seccraft_app.screens.auth.registerPaguyuban

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seccraft_app.R
import com.example.seccraft_app.ui.theme.PoppinsFamily
import com.example.seccraft_app.ui.theme.gray_88
import com.example.seccraft_app.ui.theme.icon_faded
import com.example.seccraft_app.ui.theme.primary

@Composable
fun TextFieldRegPag(
    title: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    heightMod : Modifier = Modifier,
    type : String = "text",
    value: (String) -> Unit
) {

    var isError by rememberSaveable { mutableStateOf(false) }
    var password = false
    var errorMessage = ""

    var text by remember {
        mutableStateOf("")
    }
    var open by remember {
        mutableStateOf(false)
    }

    if (type=="password" && text != ""){
        val charMin = 6
        password = true
        isError = text.length < charMin
        errorMessage = "Password minimal 6 karakter"
    }

    if (type=="email" && text!=""){
        isError = !isEmailValid(text)
        errorMessage = "email tidak valid"
    }

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
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        OutlinedTextField(
            value = text,
            onValueChange = {
                val regex = Regex("[0-9]*")
                if (keyboardType == KeyboardType.Number){
                    if (it.matches(regex)) {
                        text = it
                    }
                }
                else{
                    text = it
                }

                            },
            isError = isError,
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(10.dp),
            singleLine = singleLine,
            modifier = heightMod
                .padding(top = 8.dp)
                .fillMaxWidth(),
            visualTransformation = if (!open && password) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (password) trailingIconView else null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = primary,
                unfocusedBorderColor = icon_faded
            )
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontFamily = PoppinsFamily,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        value(text)
    }
}