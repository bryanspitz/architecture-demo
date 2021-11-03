package com.bryanspitz.architecturedemo.testutil

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.mockito.kotlin.mock
import kotlin.coroutines.EmptyCoroutineContext

fun <R> (() -> Flow<R>).stubFlowReturn(vararg value: R) = stubReturn(flowOf(*value))
fun <R> (() -> Flow<R>).stubSharedFlow(): MutableSharedFlow<R> {
	val flow = MutableSharedFlow<R>()
	stubReturn(flow)
	return flow
}

fun <R> (() -> Flow<R>).stubStateFlow(initial: R): MutableStateFlow<R> {
	val flow = MutableStateFlow(initial)
	stubReturn(flow)
	return flow
}

inline fun <reified T : Any, R> sharedMock(
	crossinline function: T.() -> Flow<R>
): Pair<T, MutableSharedFlow<R>> {
	val mock = mock<T>()
	val flow = ref { mock.function() }.stubSharedFlow()
	return mock to flow
}

inline fun <reified T : Any, R> stateMock(
	crossinline function: T.() -> Flow<R>,
	initial: R
): Pair<T, MutableStateFlow<R>> {
	val mock = mock<T>()
	val flow = ref { mock.function() }.stubStateFlow(initial)
	return mock to flow
}

suspend fun <T> Flow<T>.test(unconfined: Boolean = false, assertions: suspend (FlowTestObserver<T>) -> Unit) {
	val scope = TestCoroutineScope()
	val observer = FlowTestObserver(this)
	val job = scope.launch {
		launch(if (unconfined) Dispatchers.Unconfined else EmptyCoroutineContext) {
			observer.observe()
		}
		launch {
			assertions(observer)
			scope.cancel()
		}
	}
	job.join()
}

class FlowTestObserver<T>(private val flow: Flow<T>) {
	private val values = mutableListOf<T>()
	private val errors = mutableListOf<Throwable>()
	
	val valueCount get() = values.size
	val errorCount get() = errors.size
	
	suspend fun observe() {
		flow.catch { errors.add(it) }
			.collectLatest { values.add(it) }
	}
	
	fun assertValues(vararg values: T) {
		this.values.shouldContainExactly(*values)
	}
	
	fun valueAt(index: Int, assertion: T.() -> Unit) {
		values[index].assertion()
	}
	
	fun assertValueAt(index: Int, value: T) {
		values[index] shouldBe value
	}
	
	fun errorAt(index: Int, assertion: Throwable.() -> Unit) {
		errors[index].assertion()
	}
	
	fun assertErrorAt(index: Int, e: Throwable) {
		errors[index] shouldBe e
	}
}

fun FlowTestObserver<*>.assertNoValues() = assertValues()
fun <T> FlowTestObserver<T>.assertLatestValue(value: T) = assertValueAt(valueCount - 1, value)
fun <T> FlowTestObserver<T>.latestValue(
	assertion: T.() -> Unit
) = valueAt(valueCount - 1, assertion)

inline fun <reified E : Throwable> FlowTestObserver<*>.assertLatestError() =
	errorAt(errorCount - 1) { this.shouldBeInstanceOf<E>() }