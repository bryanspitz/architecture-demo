package com.bryanspitz.architecturedemo.ui.lists

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bryanspitz.architecturedemo.ui.lists.list.ListScreen

private const val ROUTE_LIST = "list"
private const val ARG_LIST_ID = "listId"

fun NavGraphBuilder.listsNavigation() {
	composable(
		route = "$ROUTE_LIST/{$ARG_LIST_ID}",
		arguments = listOf(navArgument("listId") { type = NavType.LongType })
	) { ListScreen(listId = it.arguments!!.getLong(ARG_LIST_ID)) }
}

fun listRoute(listId: Long) = "$ROUTE_LIST/$listId"