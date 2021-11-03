package com.bryanspitz.architecturedemo.app

import android.app.Application
import android.content.Context
import com.bryanspitz.architecturedemo.repository.todo.TodoListRepositoryProvider
import com.bryanspitz.architecturedemo.repository.todo.database.TodoListDatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

class ArchitectureDemoApp : Application(), AppDependenciesProvider {
	
	private lateinit var component: ArchitectureDemoComponent
	
	override fun onCreate() {
		super.onCreate()
		
		component = DaggerArchitectureDemoComponent.factory().create(this)
	}
	
	override fun appDependencies() = component
}

@Singleton
@Component(modules = [TodoListDatabaseModule::class])
interface ArchitectureDemoComponent : AppDependencies {
	
	@Component.Factory
	interface Factory {
		fun create(@BindsInstance appContext: Context): ArchitectureDemoComponent
	}
}

interface AppDependenciesProvider {
	fun appDependencies(): AppDependencies
}

interface AppDependencies : TodoListRepositoryProvider