package com.bryanspitz.architecturedemo.testutil

import com.bryanspitz.architecturedemo.repository.todo.database.TodoListDatabase
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.stub

fun <R> TodoListDatabase.stubTransaction() {
	stub {
		onBlocking { runInSuspendTransaction<R>(any()) } doSuspendableAnswer  {
			(it.getArgument(0) as suspend () -> R)()
		}
	}
}