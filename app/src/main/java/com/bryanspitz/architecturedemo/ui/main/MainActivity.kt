package com.bryanspitz.architecturedemo.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bryanspitz.architecturedemo.R
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ArchitectureDemoTheme {
				MainActivityScreen()
			}
		}
	}
}

@Composable
fun MainActivityScreen() {
	val navController = rememberNavController()
	val onNavigate = remember { MutableSharedFlow<String>() }
	
	LaunchedEffect(Unit) {
		launch { NavigationFeature(navController, onNavigate).start() }
		awaitCancellation()
	}
	
	MainActivityLayout(
		navController = navController,
		onTabSelected = onNavigate
	)
}

@Composable
fun MainActivityLayout(
	navController: NavHostController,
	onTabSelected: MutableSharedFlow<String>
) {
	val currentStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = currentStackEntry?.destination
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(stringResource(id = R.string.app_name)) }
			)
		},
		bottomBar = {
			MainBottomNavigation(
				{ route -> currentDestination?.hierarchy?.any { it.route == route } == true },
				onTabSelected
			)
		}
	) {
		MainNavHost(navController, Modifier.padding(it))
	}
}