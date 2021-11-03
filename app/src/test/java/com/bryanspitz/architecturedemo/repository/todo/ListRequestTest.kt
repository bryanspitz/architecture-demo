package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItem
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoItemMapper
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ItemDao
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import com.bryanspitz.architecturedemo.testutil.assertLatestValue
import com.bryanspitz.architecturedemo.testutil.ref
import com.bryanspitz.architecturedemo.testutil.stubSharedFlow
import com.bryanspitz.architecturedemo.testutil.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock

private const val LIST_ID = 123L
private const val LIST_NAME = "My List"

internal class ListRequestTest : BehaviorSpec({
	val listDao: ListDao = mock()
	val itemDao: ItemDao = mock()
	val request = ListRequest(listDao, itemDao, DbTodoListMapper(DbTodoItemMapper()))
	
	Given("database methods are stubbed") {
		val listResult = ref { listDao.getList(LIST_ID) }.stubSharedFlow()
		val itemsResult = ref { itemDao.getListItems(LIST_ID) }.stubSharedFlow()
		
		When("list is requested") {
			request.list(LIST_ID).test(unconfined = true) { listTest ->
				
				And("database emits list") {
					val list = DbTodoList(LIST_ID, LIST_NAME)
					listResult.emit(list)
					
					And("database emits items") {
						val items = dbItems(5)
						itemsResult.emit(items)
						
						Then("emit converted list with items") {
							listTest.assertValues(
								TodoListWithItems(
									TodoList(LIST_ID, LIST_NAME, itemCount = 5),
									domainItems(5)
								)
							)
						}
						
						And("database emits identical list") {
							listResult.emit(list)
							
							Then("do not emit again") {
								listTest.valueCount shouldBe 1
							}
						}
						And("database emits identical items") {
							itemsResult.emit(items)
							
							Then("do not emit again") {
								listTest.valueCount shouldBe 1
							}
						}
						And("database emits updated list") {
							listResult.emit(DbTodoList(LIST_ID, "different"))
							
							Then("emit new domain objects") {
								listTest.assertLatestValue(
									TodoListWithItems(
										TodoList(LIST_ID, "different", itemCount = 5),
										domainItems(5)
									)
								)
							}
						}
						And("database emits updated items") {
							itemsResult.emit(dbItems(6))
							
							Then("emit new domain objects") {
								listTest.assertLatestValue(
									TodoListWithItems(
										TodoList(LIST_ID, LIST_NAME, itemCount = 6),
										domainItems(6)
									)
								)
							}
						}
					}
				}
			}
		}
	}
})

private fun dbItems(itemCount: Int) = List(itemCount) {
	DbTodoItem(
		id = it.toLong(),
		"Item $it",
		isChecked = it % 2 == 0,
		listId = LIST_ID
	)
}

private fun domainItems(itemCount: Int) = List(itemCount) {
	TodoItem(
		id = it.toLong(),
		"Item $it",
		isChecked = it % 2 == 0
	)
}