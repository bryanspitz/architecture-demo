package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val ITEM_ID = 123L

internal class DeleteItemRequestTest : BehaviorSpec({
	val database: ItemDao = mock()
	
	val request = DeleteItemRequest(database)
	
	Given("no conditions") {
		When("item is deleted") {
			request.deleteItem(ITEM_ID)
			
			Then("delete item with correct id from database") {
				verify(database).delete(argForWhich { id == ITEM_ID })
			}
		}
	}
})