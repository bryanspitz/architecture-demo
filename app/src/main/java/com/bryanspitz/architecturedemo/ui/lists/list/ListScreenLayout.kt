package com.bryanspitz.architecturedemo.ui.lists.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ListScreenLayout(
	list: TodoListWithItems,
	onAdd: MutableSharedFlow<String>,
	onItemClick: MutableSharedFlow<TodoItem>
) {
	LazyColumn(modifier = Modifier.fillMaxSize()) {
		items(list.items, TodoItem::id) {
			ItemRow(it, onItemClick)
		}
		item("edit") {
			ItemAddRow(onAdd)
		}
	}
}

@Composable
fun ItemRow(
	item: TodoItem,
	onItemClick: MutableSharedFlow<TodoItem>
) {
	val scope = rememberCoroutineScope()
	val style = MaterialTheme.typography.body1.let {
		if (item.isChecked) {
			it.copy(textDecoration = TextDecoration.LineThrough)
		} else {
			it
		}
	}
	
	Surface {
		Text(
			text = item.name,
			style = style,
			modifier = Modifier
				.clickable { scope.launch { onItemClick.emit(item) } }
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		)
	}
}

@Composable
fun ItemAddRow(
	onAdd: MutableSharedFlow<String>
) {
	var editText by remember { mutableStateOf("") }
	val scope = rememberCoroutineScope()
	
	Surface {
		BasicTextField(
			value = editText,
			onValueChange = { editText = it },
			textStyle = MaterialTheme.typography.body1,
			maxLines = 1,
			keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
			keyboardActions = KeyboardActions(
				onDone = {
					scope.launch {
						onAdd.emit(editText)
						editText = ""
					}
				}
			),
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		)
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
			MutableSharedFlow(),
			MutableSharedFlow()
		)
	}
}