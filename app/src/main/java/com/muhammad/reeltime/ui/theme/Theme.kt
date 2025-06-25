package com.muhammad.reeltime.ui.theme

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = ReelTimeGreen,
    background = ReelTimeBlack,
    surface = ReelTimeDarkGray,
    secondary = ReelTimeWhite,
    tertiary = ReelTimeWhite,
    primaryContainer = ReelTimeGreen30,
    onPrimary = ReelTimeBlack,
    onBackground = ReelTimeWhite,
    onSurface = ReelTimeWhite,
    onSurfaceVariant = ReelTimeGray,
    error = ReelTimeDarkRed,
    errorContainer = ReelTimeDarkRed5
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ReelTimeTheme(content: @Composable () -> Unit) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        motionScheme = MotionScheme.expressive()
    )
}

