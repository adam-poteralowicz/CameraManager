package com.apap.cameraManager.util

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*
import com.apap.cameraManager.presentation.view.Route

fun NavController.navigateToCameraDetails(
    args: Bundle,
    route: Route,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val routeLink = NavDeepLinkRequest.Builder
        .fromUri(NavDestination.createRoute(route.name).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    deepLinkMatch?.let {
        navigate(it.destination.id, args, navOptions, navigatorExtras)
    } ?: navigate(route.name, navOptions, navigatorExtras)
}