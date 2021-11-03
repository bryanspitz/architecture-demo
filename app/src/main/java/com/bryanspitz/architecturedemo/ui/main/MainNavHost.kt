package com.bryanspitz.architecturedemo.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = AppScreen.LISTS.route,
		modifier = modifier
	) {
		mainNavigation(navController)
	}
}