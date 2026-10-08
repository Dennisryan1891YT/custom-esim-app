package com.example.customesim.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2A7DE1),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCEBFF),
    secondary = Color(0xFF5D5FEF),
    onSecondary = Color.White,
    background = Color(0xFFF6F8FF),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onBackground = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE8F0FF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8AB4F8),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1F2A3D),
    secondary = Color(0xFFB5C7FF),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    onBackground = Color.White,
    surfaceVariant = Color(0xFF2A2D33)
)

@Composable
fun CustomESimTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
