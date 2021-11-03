package com.bryanspitz.architecturedemo.repository.todo.database

import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import javax.inject.Inject

class DbTodoListMapper @Inject constructor(
	private val itemMapper: DbTodoItemMapper
) {
	
	fun mapToDomain(dbList: DbTodoList, count: Int) = with(dbList) { TodoList(id, name, count) }
	
	fun mapToDomainWithItems(
		dbList: DbTodoList,
		items: List<DbTodoItem>
	) = TodoListWithItems(
		mapToDomain(dbList, items.size),
		items.map { itemMapper.mapToDomain(it) }
	)
	
	fun mapFromDomain(list: TodoList) = with(list) { DbTodoList(id, name) }
}