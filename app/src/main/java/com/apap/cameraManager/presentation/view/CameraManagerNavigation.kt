package com.apap.cameraManager.presentation.view

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apap.cameraManager.util.navigateToCameraDetails

@Composable
fun CameraManagerNavigation(navController: NavHostController, onBackPressed: () -> Unit) {
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