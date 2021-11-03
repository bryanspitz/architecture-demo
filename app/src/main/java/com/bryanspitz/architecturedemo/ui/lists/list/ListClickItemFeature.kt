package com.bryanspitz.architecturedemo.ui.lists.list

import com.bryanspitz.architecturedemo.architecture.Feature
import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.repository.todo.UpdateItemRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ListClickItemFeature @Inject constructor(
	private val updateRequest: UpdateItemRequest,
	@ListId private val listId: Long,
	@Click private val onClick: MutableSharedFlow<TodoItem>
) : Feature {
	
	override suspend fun start() {
		onClick.collectLatest {
			updateRequest.updateItem(
				it.copy(isChecked = !it.isChecked),
				listId
			)
		}
	}
}