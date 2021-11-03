package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_NAME = "new list"

internal class AddListRequestTest : BehaviorSpec({
	val database: ListDao = mock()
	
	val request = AddListRequest(database)
	
	Given("no conditions") {
		When("an item is added") {
			request.addList(LIST_NAME)
			
			Then("list with unset id is added to db") {
				verify(database).insert(DbTodoList(id = 0, LIST_NAME))
			}
		}
	}
})