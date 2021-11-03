package com.bryanspitz.architecturedemo.repository.todo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class DbTodoItem(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val name: String,
	val isChecked: Boolean,
	val listId: Long
)
