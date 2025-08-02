package com.fac23.kmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = RobotoTypography(),
        colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}*/

@Composable
fun getColorTheme(): DarkModeColors {
    val isDarkMode = false

    val Primary = Color(0xFFF2831D)
    val PrimaryAccent = Color(0xFFFFE8DF)
    val Secondary = Color(0xFF1FA147/*00796B*/)
    val SecondaryAccent = Color(0xFFC4E8CA)
    val BackgroundColor = if (isDarkMode) Color(0xFF1E1C1C) else Color.White
    val BackgroundMainColor = Primary
    val BackgroundSecondaryColor = if (isDarkMode) Color(0xFF1E1C1C) else Secondary
    val DividerColor = Color(0xFFEEEEEE)
    val FieldActive = Primary
    val FieldInactive = Color(0xFF5c5b5b)

    val TextColor = Color(0xFF5c5b5b) //Negro-gris
    val TextAccentColor = Color(0xFF848484) //Negro-gris-mas-claro

    val TextSecondaryColor = Secondary
    val TextSecondaryAccentColor = Color.White

    val TextMainColor = Primary //Naranja


    return DarkModeColors(

        //--------
        primary = Primary,
        primaryAccent = PrimaryAccent,
        secondary = Secondary,
        secondaryAccent = SecondaryAccent,

        backgroundColor = BackgroundColor,
        backgroundMainColor = BackgroundMainColor,
        backgroundSecondaryColor = BackgroundSecondaryColor,
        textColor = TextColor,
        textAccentColor = TextAccentColor,
        textSecondaryColor = TextSecondaryColor,
        textSecondaryAccentColor = TextSecondaryAccentColor,
        textMainColor = TextMainColor,
        dividerColor = DividerColor,
        fieldActive = FieldActive,
        fieldInactive = FieldInactive
    )
}

data class DarkModeColors(

    //---------------
    val primary: Color,
    val primaryAccent: Color,
    val secondary: Color,
    val secondaryAccent: Color,

    val backgroundColor: Color,
    val backgroundMainColor: Color,
    val backgroundSecondaryColor: Color,
    val textColor: Color,
    val textAccentColor: Color,
    val textSecondaryColor: Color,
    val textSecondaryAccentColor: Color,
    val textMainColor: Color,
    val dividerColor: Color,
    val fieldActive: Color,
    val fieldInactive: Color
)