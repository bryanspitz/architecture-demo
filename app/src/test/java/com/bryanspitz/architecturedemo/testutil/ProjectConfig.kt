package com.bryanspitz.architecturedemo.testutil

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

class ProjectConfig : AbstractProjectConfig() {
	override val isolationMode = IsolationMode.InstancePerLeaf
}