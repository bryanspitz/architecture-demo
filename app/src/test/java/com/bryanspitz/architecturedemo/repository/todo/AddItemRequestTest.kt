package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_ID = 123L
private const val ITEM_NAME = "a new item"

internal class AddItemRequestTest : BehaviorSpec({
	val database: ItemDao = mock()
	
	val request = AddItemRequest(database)
	
	Given("no conditions") {
		When("an item is added") {
			request.addItem(LIST_ID, ITEM_NAME)
			
			Then("item with unset id is added to db") {
				verify(database).insert(DbTodoItem(id = 0, ITEM_NAME, isChecked = false, LIST_ID))
			}
		}
	}
})