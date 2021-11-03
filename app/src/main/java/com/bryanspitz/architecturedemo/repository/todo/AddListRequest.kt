package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import javax.inject.Inject

@AllOpen
class AddListRequest @Inject constructor(
	private val database: ListDao
) {
	suspend fun addList(name: String) {
		database.insert(DbTodoList(name = name))
	}
}