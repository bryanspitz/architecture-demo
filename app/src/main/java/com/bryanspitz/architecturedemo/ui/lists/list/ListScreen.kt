package com.bryanspitz.architecturedemo.ui.lists.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bryanspitz.architecturedemo.architecture.FeatureSet
import com.bryanspitz.architecturedemo.architecture.FeatureSetProvider
import com.bryanspitz.architecturedemo.architecture.ScreenScope
import com.bryanspitz.architecturedemo.architecture.appDependency
import com.bryanspitz.architecturedemo.model.TodoItem
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.model.TodoListWithItems
import com.bryanspitz.architecturedemo.repository.todo.ListRequest
import com.bryanspitz.architecturedemo.repository.todo.TodoListRepositoryProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Qualifier

@Composable
fun ListScreen(listId: Long) {
	val context = LocalContext.current
	val component = remember {
		DaggerListComponent.factory().create(
			listId,
			context.appDependency()
		)
	}
	val list by component.list.collectAsState(TodoListWithItems(TodoList(0L, "", 0), emptyList()))
	
	LaunchedEffect(Unit) {
		component.features().launchAll()
	}
	
	ListScreenLayout(
		list = list,
		onAdd = component.onAdd,
		onItemClick = component.onClick
	)
}

@ScreenScope
@Component(
	dependencies = [TodoListRepositoryProvider::class],
	modules = [ListModule::class]
)
interface ListComponent : FeatureSetProvider {
	
	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance @ListId listId: Long,
			repositoryProvider: TodoListRepositoryProvider
		): ListComponent
	}
	
	val list: Flow<TodoListWithItems>
	
	@get:Add val onAdd: MutableSharedFlow<String>
	@get:Click val onClick: MutableSharedFlow<TodoItem>
}

@Module
class ListModule {
	@get:Provides @get:Add val onAdd = MutableSharedFlow<String>()
	@get:Provides @get:Click val onClick = MutableSharedFlow<TodoItem>()
	
	@ScreenScope @Provides fun list(
		@ListId listId: Long,
		listRequest: ListRequest
	) = listRequest.list(listId)
	
	@Provides fun features(
		add: ListAddItemFeature,
		click: ListClickItemFeature
	) = FeatureSet(add, click)
}

@Qualifier annotation class ListId
@Qualifier annotation class Add
@Qualifier annotation class Click