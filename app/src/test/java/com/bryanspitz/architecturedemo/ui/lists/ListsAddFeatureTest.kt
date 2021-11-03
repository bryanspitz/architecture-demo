package com.bryanspitz.architecturedemo.ui.lists

import com.bryanspitz.architecturedemo.repository.todo.AddListRequest
import com.bryanspitz.architecturedemo.testutil.startAndTest
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.MutableSharedFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class ListsAddFeatureTest : BehaviorSpec({
	val addRequest: AddListRequest = mock()
	val onAdd = MutableSharedFlow<Unit>()
	
	val feature = ListsAddFeature(addRequest, onAdd)
	
	Given("feature is started") {
		feature.startAndTest {
			
			When("onAdd emits an event") {
				onAdd.emit(Unit)
				
				Then("call add request with default name") {
					verify(addRequest).addList("New List")
				}
			}
		}
	}
})