package com.bryanspitz.architecturedemo.ui.lists.list

import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.repository.todo.UpdateItemRequest
import com.bryanspitz.architecturedemo.testutil.startAndTest
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.MutableSharedFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_ID = 123L
private const val ITEM_ID = 456L
private const val ITEM_NAME = "Item"

internal class ListClickItemFeatureTest : BehaviorSpec({
	val updateRequest: UpdateItemRequest = mock()
	val onClick = MutableSharedFlow<TodoItem>()
	
	val feature = ListClickItemFeature(updateRequest, LIST_ID, onClick)
	
	Given("feature is started") {
		feature.startAndTest {
			
			When("click event is received for unchecked item") {
				onClick.emit(TodoItem(ITEM_ID, ITEM_NAME, isChecked = false))
				
				Then("update repository with checked item") {
					verify(updateRequest).updateItem(
						TodoItem(ITEM_ID, ITEM_NAME, isChecked = true),
						LIST_ID
					)
				}
			}
			When("click event is received for checked item") {
				onClick.emit(TodoItem(ITEM_ID, ITEM_NAME, isChecked = true))
				
				Then("update repository with unchecked item") {
					verify(updateRequest).updateItem(
						TodoItem(ITEM_ID, ITEM_NAME, isChecked = false),
						LIST_ID
					)
				}
			}
		}
	}
})