package com.bryanspitz.architecturedemo.architecture

import android.content.Context
import com.bryanspitz.architecturedemo.app.AppDependenciesProvider

inline fun <reified T> Context.appDependency() = (applicationContext as AppDependenciesProvider)
	.appDependencies() as T