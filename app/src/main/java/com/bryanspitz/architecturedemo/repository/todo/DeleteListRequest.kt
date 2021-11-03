package com.bryanspitz.architecturedemo.repository.todo

import androidx.room.withTransaction
import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import com.bryanspitz.architecturedemo.repository.todo.database.TodoListDatabase
import javax.inject.Inject

@AllOpen
class DeleteListRequest @Inject constructor(
	private val database: TodoListDatabase,
	private val listDao: ListDao,
	private val itemDao: ItemDao
) {
	suspend fun deleteList(id: Long) {
		database.runInSuspendTransaction {
			listDao.delete(DbTodoList(id = id, name = ""))
			itemDao.deleteAll(id)
		}
	}
}