package com.bryanspitz.architecturedemo.repository.todo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class DbTodoList(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val name: String
)

