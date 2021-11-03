package com.bryanspitz.architecturedemo.testutil

import org.mockito.kotlin.doReturnConsecutively
import org.mockito.kotlin.stub

inline fun <reified R> (() -> R).stubReturn(vararg value: R) = stub {
	on { invoke() } doReturnConsecutively value.toList()
}

inline fun <reified R> (suspend () -> R).stubReturn(vararg value: R) = stub {
	onBlocking { invoke() } doReturnConsecutively value.toList()
}

fun <R> ref(block: () -> R) = block
fun <R> refS(block: suspend () -> R) = block