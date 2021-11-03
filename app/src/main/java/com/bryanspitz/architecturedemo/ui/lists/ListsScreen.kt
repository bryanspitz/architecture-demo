package com.bryanspitz.architecturedemo.ui.lists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bryanspitz.architecturedemo.architecture.FeatureSet
import com.bryanspitz.architecturedemo.architecture.FeatureSetProvider
import com.bryanspitz.architecturedemo.architecture.ScreenScope
import com.bryanspitz.architecturedemo.architecture.appDependency
import com.bryanspitz.architecturedemo.model.TodoList
import com.bryanspitz.architecturedemo.repository.todo.ListsRequest
import com.bryanspitz.architecturedemo.repository.todo.TodoListRepositoryProvider
import com.bryanspitz.architecturedemo.ui.shared.Question
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Qualifier

@Composable
fun ListsScreen(
	navController: NavController
) {
	val context = LocalContext.current
	val component = remember {
		DaggerListsComponent.factory().create(
			navController,
			context.appDependency()
		)
	}
	val lists by component.lists.collectAsState(initial = emptyList())
	
	LaunchedEffect(null) {
		component.features().launchAll()
	}
	
	ListsScreenLayout(
		lists = lists,
		deleteConfirmDialog = component.deleteConfirmDialog,
		onAdd = component.onAdd,
		onDelete = component.onDelete,
		onListClick = component.onListClick
	)
}

@ScreenScope
@Component(
	dependencies = [TodoListRepositoryProvider::class],
	modules = [ListsModule::class]
)
interface ListsComponent : FeatureSetProvider {
	
	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance navController: NavController,
			todoListRepositoryProvider: TodoListRepositoryProvider
		): ListsComponent
	}
	
	val lists: Flow<List<TodoList>>
	@get:DeleteDialog val deleteConfirmDialog: Question<TodoList, Boolean>
	
	@get:Add val onAdd: MutableSharedFlow<Unit>
	@get:Delete val onDelete: MutableSharedFlow<TodoList>
	@get:Click val onListClick: MutableSharedFlow<Long>
}

@Module
class ListsModule {
	@get:Provides @get:DeleteDialog val deleteConfirmDialog = Question<TodoList, Boolean>()
	
	@get:Provides @get:Add val onAdd = MutableSharedFlow<Unit>()
	@get:Provides @get:Delete val onDelete = MutableSharedFlow<TodoList>()
	@get:Provides @get:Click val onListClick = MutableSharedFlow<Long>()
	
	@ScreenScope @Provides fun lists(lists: ListsRequest) = lists.lists
	
	@Provides fun features(
		add: ListsAddFeature,
		delete: ListsDeleteFeature,
		click: ListsClickFeature
	) = FeatureSet(add, delete, click)
}

@Qualifier annotation class Add
@Qualifier annotation class Delete
@Qualifier annotation class DeleteDialog
@Qualifier annotation class Click