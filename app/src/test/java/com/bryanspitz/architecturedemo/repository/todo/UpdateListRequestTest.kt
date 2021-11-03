package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItemMapper
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_ID = 123L
private const val LIST_NAME = "list name"

internal class UpdateListRequestTest : BehaviorSpec({
	val database: ListDao = mock()
	
	val request = UpdateListRequest(database, DbTodoListMapper(DbTodoItemMapper()))
	
	Given("no conditions") {
		When("item is updated") {
			request.updateList(TodoList(LIST_ID, LIST_NAME, itemCount = 5))
			
			Then("update database item") {
				verify(database).update(DbTodoList(LIST_ID, LIST_NAME))
			}
		}
	}
})