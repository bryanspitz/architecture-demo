package com.bryanspitz.architecturedemo.model

data class TodoItem(
	val id: Long,
	val name: String,
	val isChecked: Boolean
)