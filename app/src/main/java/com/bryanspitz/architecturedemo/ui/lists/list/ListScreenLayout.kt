package com.bryanspitz.architecturedemo.ui.lists.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ListScreenLayout(
	list: TodoListWithItems,
	onAdd: MutableSharedFlow<String>
) {
	var editText by remember { mutableStateOf("") }
	
	LazyColumn(Modifier.fillMaxSize()) {
		items(list.items, TodoItem::id) {
			Text(it.name)
		}
		item("edit") {
			val scope = rememberCoroutineScope()
			TextField(
				value = editText,
				onValueChange = { editText = it },
				keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
				keyboardActions = KeyboardActions(
					onDone = {
						scope.launch {
							onAdd.emit(editText)
							editText = ""
						}
					}
				),
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}

@Preview
@Composable
fun ListScreenLayoutPreview() {
	ArchitectureDemoTheme {
		ListScreenLayout(
			TodoListWithItems(
				TodoList(0L, "Groceries", itemCount = 3),
				listOf(
					TodoItem(0L, "Apples", isChecked = false),
					TodoItem(1L, "Oranges", isChecked = true),
					TodoItem(2L, "Bananas", isChecked = false)
				)
			),
			MutableSharedFlow()
		)
	}
}