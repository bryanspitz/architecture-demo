package com.bryanspitz.architecturedemo.ui.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bryanspitz.architecturedemo.R
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.ui.shared.Question
import com.bryanspitz.architecturedemo.ui.theme.ArchitectureDemoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ListsScreenLayout(
	lists: List<TodoList>,
	deleteConfirmDialog: Question<TodoList, Boolean>,
	onAdd: MutableSharedFlow<Unit>,
	onDelete: MutableSharedFlow<TodoList>,
	onListClick: MutableSharedFlow<Long>
) {
	val dialogState by deleteConfirmDialog.state.collectAsState()
	dialogState?.also {
		ListDeleteConfirmDialog(item = it, onConfirm = deleteConfirmDialog::answer)
	}
	
	Box(Modifier.fillMaxSize()) {
		ListsList(
			lists,
			dialogState,
			onDelete,
			onListClick,
			Modifier.fillMaxSize()
		)
		AddButton(
			onAdd,
                Modifier
                        .align(Alignment.BottomEnd)
                        .padding(all = 16.dp)
		)
	}
}

@Preview
@Composable
private fun ListsScreenLayoutPreview() {
	ArchitectureDemoTheme {
		ListsScreenLayout(
			listOf(TodoList(0L, "Groceries", 4), TodoList(1L, "To-do", 5)),
			deleteConfirmDialog = Question(),
			MutableSharedFlow(),
			MutableSharedFlow(),
			MutableSharedFlow()
		)
	}
}

@Composable
private fun AddButton(
	onAdd: MutableSharedFlow<Unit>,
	modifier: Modifier = Modifier
) {
	val scope = rememberCoroutineScope()
	FloatingActionButton(
		onClick = { scope.launch { onAdd.emit(Unit) } },
		modifier = modifier
	) {
		Icon(Icons.Default.Add, stringResource(R.string.add_list))
	}
}