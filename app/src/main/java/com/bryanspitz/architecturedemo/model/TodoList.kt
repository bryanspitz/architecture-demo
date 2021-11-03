package com.bryanspitz.architecturedemo.model

data class TodoList(
	val id: Long,
	val name: String,
	val itemCount: Int
)

data class TodoListWithItems(
	val list: TodoList,
	val items: List<TodoItem>
)
