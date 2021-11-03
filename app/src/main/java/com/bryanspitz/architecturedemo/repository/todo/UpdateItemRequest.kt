package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItemMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import javax.inject.Inject

@AllOpen
class UpdateItemRequest @Inject constructor(
	private val database: ItemDao,
	private val mapper: DbTodoItemMapper
) {
	suspend fun updateItem(item: TodoItem, listId: Long) {
		database.update(mapper.mapFromDomain(item, listId))
	}
}