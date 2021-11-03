package com.bryanspitz.architecturedemo.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bryanspitz.architecturedemo.ui.lists.ListsScreen
import com.bryanspitz.architecturedemo.ui.lists.listsNavigation
import com.bryanspitz.architecturedemo.ui.user.UserScreen

fun NavGraphBuilder.mainNavigation(navController: NavController) {
	navigation(startDestination = "listsRoot", AppScreen.LISTS.route) {
		composable("listsRoot") { ListsScreen(navController) }
		listsNavigation()
	}
	
	composable(AppScreen.USER.route) { UserScreen() }
}