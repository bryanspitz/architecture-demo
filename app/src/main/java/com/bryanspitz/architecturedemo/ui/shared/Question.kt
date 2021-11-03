package com.bryanspitz.architecturedemo.ui.shared

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import java.util.concurrent.CancellationException

class Question<Q, A> {
	private val _state = MutableStateFlow<Q?>(null)
	private val result = MutableSharedFlow<A>()
	
	val state: StateFlow<Q?> = _state
	
	suspend fun ask(question: Q): A {
		if (_state.value != null) {
			throw IllegalStateException("Cannot ask while another question is in progress")
		}
		try {
			_state.value = question
			return result.first()
		} catch (e: CancellationException) {
			_state.value = null
			throw e
		}
	}
	
	suspend fun answer(answer: A) {
		_state.value = null
		result.emit(answer)
	}
}