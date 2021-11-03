package com.bryanspitz.architecturedemo.ui.lists

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.bryanspitz.architecturedemo.R
import com.bryanspitz.architecturedemo.model.TodoList
import kotlinx.coroutines.launch

@Composable
fun ListDeleteConfirmDialog(
	item: TodoList,
	onConfirm: suspend (Boolean) -> Unit
) {
	val scope = rememberCoroutineScope()
	AlertDialog(
		title = { Text(stringResource(R.string.delete_list_dialog_title)) },
		text = {
			Text(
				stringResource(
					R.string.delete_list_dialog_text,
					item.name,
					item.itemCount
				)
			)
		},
		confirmButton = {
			TextButton(onClick = { scope.launch { onConfirm(true) } }) {
				Text(stringResource(R.string.delete).toUpperCase(Locale.current))
			}
		},
		dismissButton = {
			TextButton(onClick = { scope.launch { onConfirm(false) } }) {
				Text(stringResource(R.string.cancel).toUpperCase(Locale.current))
			}
		},
		onDismissRequest = { scope.launch { onConfirm(false) } }
	)
}