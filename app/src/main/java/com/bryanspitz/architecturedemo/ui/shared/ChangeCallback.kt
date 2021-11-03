package com.bryanspitz.architecturedemo.ui.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> ChangeCallback(evaluate: () -> T, onChanged: (from: T, to: T) -> Unit) {
	val oldValue = remember { mutableStateOf(evaluate()) }
	val newValue = evaluate()
	if (newValue != oldValue.value) {
		onChanged(oldValue.value, newValue)
	}
	oldValue.value = newValue
}