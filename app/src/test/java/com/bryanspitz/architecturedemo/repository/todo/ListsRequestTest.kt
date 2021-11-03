package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItemMapper
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import com.bryanspitz.architecturedemo.testutil.assertLatestValue
import com.bryanspitz.architecturedemo.testutil.sharedMock
import com.bryanspitz.architecturedemo.testutil.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class ListsRequestTest : BehaviorSpec({
	val (database, listsFlow) = sharedMock(ListDao::getLists)
	
	val request = ListsRequest(database, DbTodoListMapper(DbTodoItemMapper()))
	
	Given("lists are requested") {
		request.lists.test { listsTest ->
			
			When("database emits empty result") {
				listsFlow.emit(emptyMap())
				
				Then("emit empty list") {
					listsTest.assertValues(emptyList())
				}
			}
			When("database emits result with lists") {
				listsFlow.emit(dbLists(3))
				
				Then("emit converted lists") {
					listsTest.assertValues(domainLists(3))
				}
				
				And("database emits identical result") {
					listsFlow.emit(dbLists(3))
					
					Then("do not emit again") {
						listsTest.valueCount shouldBe 1
					}
				}
				And("database emits updated result") {
					listsFlow.emit(dbLists(2))
					
					Then("emit new result") {
						listsTest.assertLatestValue(domainLists(2))
					}
				}
			}
		}
	}
})

private fun dbLists(count: Int) = mapOf(
	*List(count) { DbTodoList(id = it.toLong(), name = "list $it") to 5 }.toTypedArray()
)

private fun domainLists(count: Int) = List(count) {
	TodoList(id = it.toLong(), name = "list $it", itemCount = 5)
}