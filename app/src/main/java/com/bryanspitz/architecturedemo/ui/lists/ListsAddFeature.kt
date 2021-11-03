package com.bryanspitz.architecturedemo.ui.lists

import com.bryanspitz.architecturedemo.architecture.Feature
import com.bryanspitz.architecturedemo.repository.todo.AddListRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ListsAddFeature @Inject constructor(
	private val addRequest: AddListRequest,
	@Add private val onAdd: MutableSharedFlow<Unit>
) : Feature {
	
	override suspend fun start() {
		onAdd.collectLatest { addRequest.addList("New List") }
	}
}