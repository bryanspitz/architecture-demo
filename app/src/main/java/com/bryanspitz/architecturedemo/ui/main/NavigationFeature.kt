package com.bryanspitz.architecturedemo.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bryanspitz.architecturedemo.architecture.Feature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class NavigationFeature(
	private val navController: NavController,
	private val onNavigate: Flow<String>
) : Feature {
	
	override suspend fun start() {
		onNavigate.collectLatest {
			navController.navigate(it) {
				popUpTo(navController.graph.findStartDestination().id) {
					saveState = true
				}
				launchSingleTop = true
				restoreState = true
			}
		}
	}
}