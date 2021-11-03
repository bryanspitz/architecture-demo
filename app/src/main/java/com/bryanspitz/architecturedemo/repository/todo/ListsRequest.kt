package com.bryanspitz.architecturedemo.repository.todo

import com.bryanspitz.architecturedemo.architecture.AllOpen
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.database.DbTodoListMapper
import com.bryanspitz.architecturedemo.repository.todo.database.ListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AllOpen
class ListsRequest @Inject constructor(
    private val database: ListDao,
    private val listMapper: DbTodoListMapper
) {
    val lists: Flow<List<TodoList>>
        get() = database.getLists()
            .distinctUntilChanged()
            .map {
            it.map { (list, count) -> listMapper.mapToDomain(list, count) }
        }
}