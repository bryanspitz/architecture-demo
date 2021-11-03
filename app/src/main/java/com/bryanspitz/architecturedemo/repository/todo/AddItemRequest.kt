package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import javax.inject.Inject

@AllOpen
class AddItemRequest @Inject constructor(
	private val database: ItemDao
) {
	suspend fun addItem(listId: Long, name: String) {
		database.insert(DbTodoItem(id = 0L, name = name, isChecked = false, listId = listId))
	}
}