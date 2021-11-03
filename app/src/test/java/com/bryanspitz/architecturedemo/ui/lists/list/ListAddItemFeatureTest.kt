package com.bryanspitz.architecturedemo.ui.lists.list

import com.bryanspitz.architecturedemo.repository.todo.AddItemRequest
import com.bryanspitz.architecturedemo.testutil.startAndTest
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.MutableSharedFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

private const val LIST_ID = 123L
private const val ITEM_NAME = "itemName"

internal class ListAddItemFeatureTest : BehaviorSpec({
	val addRequest: AddItemRequest = mock()
	val onAdd = MutableSharedFlow<String>()
	
	val feature = ListAddItemFeature(addRequest, LIST_ID, onAdd)
	
	Given("feature is started") {
		feature.startAndTest {
			
			When("click event is received for unchecked item") {
				onAdd.emit(ITEM_NAME)
				
				Then("update repository with checked item") {
					verify(addRequest).addItem(LIST_ID, ITEM_NAME)
				}
			}
		}
	}
	
})