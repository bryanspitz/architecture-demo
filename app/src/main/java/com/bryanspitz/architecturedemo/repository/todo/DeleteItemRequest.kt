package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import javax.inject.Inject

@AllOpen
class DeleteItemRequest @Inject constructor(
	private val database: ItemDao
) {
	suspend fun deleteItem(itemId: Long) {
		database.delete(DbTodoItem(id = itemId, name = "", isChecked = false, listId = 0L))
	}
}