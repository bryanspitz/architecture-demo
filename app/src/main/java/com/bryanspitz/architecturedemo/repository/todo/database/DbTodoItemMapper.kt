package com.bryanspitz.architecturedemo.repository.todo.database

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.model.TodoItem
import javax.inject.Inject

class DbTodoItemMapper @Inject constructor() {
	
	fun mapToDomain(dbItem: DbTodoItem) = with(dbItem) { TodoItem(id, name, isChecked) }
	
	fun mapFromDomain(item: TodoItem, listId: Long) = with(item) {
		DbTodoItem(id, name, isChecked, listId)
	}
}