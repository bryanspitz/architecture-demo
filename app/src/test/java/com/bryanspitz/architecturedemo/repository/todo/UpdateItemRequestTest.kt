package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItemMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val ITEM_ID = 123L
private const val ITEM_NAME = "item name"
private const val LIST_ID = 456L

internal class UpdateItemRequestTest : BehaviorSpec({
	val database: ItemDao = mock()
	
	val request = UpdateItemRequest(database, DbTodoItemMapper())
	
	Given("no conditions") {
		When("item is updated") {
			request.updateItem(TodoItem(ITEM_ID, ITEM_NAME, isChecked = true), LIST_ID)
			
			Then("update database item") {
				verify(database).update(DbTodoItem(ITEM_ID, ITEM_NAME, isChecked = true, LIST_ID))
			}
		}
	}
})