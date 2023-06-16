package com.apap.cameraManager.util

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*

fun NavController.navigateToCameraDetails(
    args: Bundle,
    route: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val routeLink = NavDeepLinkRequest.Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    deepLinkMatch?.let {
        navigate(it.destination.id, args, navOptions, navigatorExtras)
    } ?: navigate(route, navOptions, navigatorExtras)
}