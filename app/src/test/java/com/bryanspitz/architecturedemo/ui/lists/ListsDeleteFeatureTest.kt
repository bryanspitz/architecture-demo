package com.bryanspitz.architecturedemo.ui.lists

import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.DeleteListRequest
import com.bryanspitz.architecturedemo.testutil.startAndTest
import com.bryanspitz.architecturedemo.ui.shared.Question
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.MutableSharedFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

private const val LIST_ID = 3L
private const val LIST_NAME = "List"

internal class ListsDeleteFeatureTest : BehaviorSpec({
	val deleteRequest: DeleteListRequest = mock()
	val deleteDialog = Question<TodoList, Boolean>()
	val onDelete = MutableSharedFlow<TodoList>()
	
	val feature = ListsDeleteFeature(deleteRequest, deleteDialog, onDelete)
	
	Given("feature is started") {
		feature.startAndTest {
			
			When("a list with items is swiped") {
				val list = TodoList(LIST_ID, LIST_NAME, itemCount = 4)
				onDelete.emit(list)
				
				And("the user confirms the deletion") {
					deleteDialog.answer(true)
					
					Then("delete the list") {
						verify(deleteRequest).deleteList(LIST_ID)
					}
				}
				And("the user cancels the deletion") {
					deleteDialog.answer(false)
					
					Then("do not delete the list") {
						verifyNoInteractions(deleteRequest)
					}
				}
			}
			When("a list without items is swiped") {
				onDelete.emit(TodoList(LIST_ID, LIST_NAME, itemCount = 0))
				
				Then("delete the list") {
					verify(deleteRequest).deleteList(LIST_ID)
				}
			}
		}
	}
})