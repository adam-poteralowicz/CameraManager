package com.apap.cameraManager.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.apap.cameraManager.ui.theme.CameraManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraManagerTheme {
                val navController = rememberNavController()
                CameraManagerNavigation(navController) { onBackPressedDispatcher.onBackPressed() }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CameraManagerTheme {
            val navController = rememberNavController()
            CameraManagerNavigation(navController) { onBackPressedDispatcher.onBackPressed() }
        }
    }
}