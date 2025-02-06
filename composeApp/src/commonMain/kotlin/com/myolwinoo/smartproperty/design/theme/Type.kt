package com.myolwinoo.smartproperty.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import smartproperty.composeapp.generated.resources.OpenSans_Bold
import smartproperty.composeapp.generated.resources.OpenSans_ExtraBold
import smartproperty.composeapp.generated.resources.OpenSans_Light
import smartproperty.composeapp.generated.resources.OpenSans_Medium
import smartproperty.composeapp.generated.resources.OpenSans_Regular
import smartproperty.composeapp.generated.resources.OpenSans_SemiBold
import smartproperty.composeapp.generated.resources.Poppins_Black
import smartproperty.composeapp.generated.resources.Poppins_Bold
import smartproperty.composeapp.generated.resources.Poppins_ExtraBold
import smartproperty.composeapp.generated.resources.Poppins_Light
import smartproperty.composeapp.generated.resources.Poppins_Medium
import smartproperty.composeapp.generated.resources.Poppins_Regular
import smartproperty.composeapp.generated.resources.Poppins_SemiBold
import smartproperty.composeapp.generated.resources.Poppins_Thin
import smartproperty.composeapp.generated.resources.Res

private val displayFontFamily
    @Composable
    get() = FontFamily(
        fonts = listOf(
            Font(Res.font.Poppins_Regular, FontWeight.Normal),
            Font(Res.font.Poppins_Bold, FontWeight.Bold),
            Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold),
            Font(Res.font.Poppins_ExtraBold, FontWeight.ExtraBold),
            Font(Res.font.Poppins_Thin, FontWeight.Thin),
            Font(Res.font.Poppins_Medium, FontWeight.Medium),
            Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold),
            Font(Res.font.Poppins_Light, FontWeight.Light),
            Font(Res.font.Poppins_ExtraBold, FontWeight.ExtraBold),
            Font(Res.font.Poppins_Black, FontWeight.Black),
        )
    )

private val bodyFontFamily
    @Composable
    get() = FontFamily(
        fonts = listOf(
            Font(Res.font.OpenSans_Regular, FontWeight.Normal),
            Font(Res.font.OpenSans_Bold, FontWeight.Bold),
            Font(Res.font.OpenSans_SemiBold, FontWeight.SemiBold),
            Font(Res.font.OpenSans_ExtraBold, FontWeight.ExtraBold),
            Font(Res.font.OpenSans_Medium, FontWeight.Medium),
            Font(Res.font.OpenSans_SemiBold, FontWeight.SemiBold),
            Font(Res.font.OpenSans_Light, FontWeight.Light),
            Font(Res.font.OpenSans_ExtraBold, FontWeight.ExtraBold),
        )
    )

// Default Material 3 typography values
private val baseline = Typography()

val AppTypography
    @Composable
    get() = Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )

