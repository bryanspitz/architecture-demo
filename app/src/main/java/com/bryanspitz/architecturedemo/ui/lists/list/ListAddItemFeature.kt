package com.bryanspitz.architecturedemo.ui.lists.list

import com.bryanspitz.architecturedemo.architecture.Feature
import com.bryanspitz.architecturedemo.repository.todo.AddItemRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ListAddItemFeature @Inject constructor(
	private val addRequest: AddItemRequest,
	@ListId private val listId: Long,
	@Add private val onAdd: MutableSharedFlow<String>
) : Feature {
	
	override suspend fun start() {
		onAdd.collectLatest { addRequest.addItem(listId, it) }
	}
}