package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import javax.inject.Inject

@AllOpen
class UpdateListRequest @Inject constructor(
	private val database: ListDao,
	private val listMapper: DbTodoListMapper
) {
	suspend fun updateList(list: TodoList) {
		database.update(listMapper.mapFromDomain(list))
	}
}