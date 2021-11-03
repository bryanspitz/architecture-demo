package com.bryanspitz.architecturedemo.testutil

import com.bryanspitz.architecturedemo.architecture.Feature
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope

suspend fun Feature.startAndTest(tests: suspend Job.() -> Unit) {
	launchAndTest(this::start, tests)
}

suspend fun launchAndTest(underTest: suspend () -> Unit, tests: suspend Job.() -> Unit) {
	val scope = TestCoroutineScope()
	scope.launch {
		val job = launch { underTest() }
		launch {
			job.tests()
			job.cancel()
		}
	}.join()
}