package com.example.seccraft_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.seccraft_app.R

val PoppinsFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Poppins = Typography(
    bodyLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),

    displayLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),

    displayMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    headlineLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),

    titleLarge =TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),


    /* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
)

