package com.bryanspitz.architecturedemo.ui.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainBottomNavigation(
	isCurrentNavigation: (String) -> Boolean,
	onNavigate: MutableSharedFlow<String>
) {
	BottomNavigation {
		val scope = rememberCoroutineScope()
		
		AppScreen.values().forEach { screen ->
			BottomNavigationItem(
				icon = {},
				label = { Text(stringResource(id = screen.label)) },
				selected = isCurrentNavigation(screen.route),
				onClick = { scope.launch { onNavigate.emit(screen.route) } }
			)
		}
	}
}

@Preview
@Composable
fun MainBottomNavigationPreview() {
	var currentRoute by remember { mutableStateOf(AppScreen.LISTS.route) }
	val onNavigate = remember { MutableSharedFlow<String>() }
	
	LaunchedEffect(null) {
		onNavigate.collectLatest { currentRoute = it }
	}
	
	ArchitectureDemoTheme {
		MainBottomNavigation(
			isCurrentNavigation = { it == currentRoute },
			onNavigate = onNavigate
		)
	}
}