package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import com.bryanspitz.architecturedemo.repository.todo.database.TodoListDatabase
import com.bryanspitz.architecturedemo.testutil.stubTransaction
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_ID = 123L

internal class DeleteListRequestTest : BehaviorSpec({
	val database: TodoListDatabase = mock()
	database.stubTransaction<Unit>()
	val listDao: ListDao = mock()
	val itemDao: ItemDao = mock()
	
	val request = DeleteListRequest(database, listDao, itemDao)
	
	Given("no conditions") {
		When("a list is deleted") {
			request.deleteList(LIST_ID)
			
			Then("the list record is deleted") {
				verify(listDao).delete(argForWhich { id == LIST_ID })
				verify(itemDao).deleteAll(LIST_ID)
			}
		}
	}
})