package com.apap.cameraManager.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apap.cameraManager.ui.theme.CameraManagerTheme
import com.apap.cameraManager.util.navigateToCameraDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.List.name) {
                    composable(Route.List.name) {
                        MainScreen(navigateToCameraDetails = { device ->
                            val bundle = Bundle()
                            bundle.putParcelable("device", device)
                            navController.navigateToCameraDetails(
                                args = bundle,
                                route = Route.Details,
                            )
                        })
                        BackHandler(enabled = true) {}
                    }
                    composable(Route.Details.name) { entry ->
                        CameraDetailsScreen(
                            device = entry.arguments?.getParcelable("device"),
                            onBackPressedCallback = { onBackPressed() }
                        )
                        BackHandler(enabled = true) {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CameraManagerTheme {
            MainScreen {}
        }
    }
}