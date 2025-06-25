package com.muhammad.reeltime.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.muhammad.reeltime.main.navigation.AppNavigation
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.theme.ReelTimeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            viewModel.state.value.isLoggedIn
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.Companion.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            ReelTimeTheme {
                val navHostController = rememberNavController()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val isLoggedIn = state.isLoggedIn
                AppNavigation(navHostController = navHostController, isLoggedIn = isLoggedIn)
            }
        }
    }
}