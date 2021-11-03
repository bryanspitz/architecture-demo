package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@AllOpen
class ListRequest @Inject constructor(
	private val listDao: ListDao,
	private val itemDao: ItemDao,
	private val listMapper: DbTodoListMapper
) {
	fun list(id: Long): Flow<TodoListWithItems> = combine(
		listDao.getList(id),
		itemDao.getListItems(id)
	) { list, items -> listMapper.mapToDomainWithItems(list, items) }
		.distinctUntilChanged()
}