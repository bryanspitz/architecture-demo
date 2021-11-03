package com.bryanspitz.architecturedemo.ui.lists

import com.bryanspitz.architecturedemo.architecture.Feature
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.DeleteListRequest
import com.bryanspitz.architecturedemo.ui.shared.Question
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class ListsDeleteFeature @Inject constructor(
    private val deleteRequest: DeleteListRequest,
    @DeleteDialog private val deleteDialog: Question<TodoList, Boolean>,
    @Delete private val onDelete: MutableSharedFlow<TodoList>
) : Feature {

    override suspend fun start() {
        onDelete
            .filter { it.itemCount == 0 || deleteDialog.ask(it) }
            .collectLatest { deleteRequest.deleteList(it.id) }
    }
}