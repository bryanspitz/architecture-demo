package com.bryanspitz.architecturedemo.architecture

import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class FeatureSet(private vararg val features: Feature) {
	
	suspend fun launchAll() {
		supervisorScope {
			features.forEach {
				launch { it.start() }
			}
		}
	}
}

interface FeatureSetProvider {
	fun features(): FeatureSet
}