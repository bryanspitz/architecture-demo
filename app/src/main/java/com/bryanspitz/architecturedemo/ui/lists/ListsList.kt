package com.bryanspitz.architecturedemo.ui.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bryanspitz.architecturedemo.R
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.ui.shared.ChangeCallback
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ListsList(
	lists: List<TodoList>,
	deleteItem: TodoList?,
	onListDeleted: MutableSharedFlow<TodoList>,
	onListClick: MutableSharedFlow<Long>,
	modifier: Modifier = Modifier
) {
	LazyColumn(modifier = modifier) {
		items(lists, TodoList::id) {
			DismissableRow(it, deleteItem, onListDeleted, onListClick)
		}
	}
}

@Preview
@Composable
private fun ListsListPreview() {
	ArchitectureDemoTheme {
		ListsList(
			listOf(TodoList(0L, "Groceries", 4), TodoList(1L, "To-do", 5)),
			deleteItem = null,
			MutableSharedFlow(),
			MutableSharedFlow()
		)
	}
}

@Composable
private fun DismissableRow(
	item: TodoList,
	deleteItem: TodoList?,
	onListDeleted: MutableSharedFlow<TodoList>,
	onListClick: MutableSharedFlow<Long>
) {
	val dismissState = rememberDismissState()
	val scope = rememberCoroutineScope()
	
	ChangeCallback({ dismissState.isDismissed(DismissDirection.EndToStart) }) { _, dismissed ->
		if (dismissed) scope.launch { onListDeleted.emit(item) }
	}
	ChangeCallback({ deleteItem?.id }) { old, new ->
		if (old == item.id && new == null) {
			scope.launch { dismissState.reset() }
		}
	}
	
	SwipeToDismiss(
		state = dismissState,
		directions = setOf(DismissDirection.EndToStart),
		background = {
			Box(
				Modifier
					.fillMaxSize()
					.background(Color.Red)
			)
		}) {
		ListRow(item, onListClick)
	}
}

@Composable
private fun ListRow(
	item: TodoList,
	onListClick: MutableSharedFlow<Long>
) {
	val scope = rememberCoroutineScope()
	
	Surface {
		Row(
			modifier = Modifier
				.clickable { scope.launch { onListClick.emit(item.id) } }
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			Text(
				item.name,
				modifier = Modifier
					.alignByBaseline()
					.padding(end = 16.dp)
			)
			Spacer(Modifier.weight(1f))
			Text(
				stringResource(R.string.list_item_count, item.itemCount),
				style = MaterialTheme.typography.body2,
				modifier = Modifier
					.alignByBaseline()
					.alpha(0.5f)
			)
		}
	}
}

@Preview
@Composable
private fun ListRowPreview() {
	ArchitectureDemoTheme {
		ListRow(TodoList(0L, "Groceries", itemCount = 4), MutableSharedFlow())
	}
}