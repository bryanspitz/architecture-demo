package com.bryanspitz.architecturedemo.repository.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.bryanspitz.architecturedemo.architecture.AllOpen
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TodoListDatabaseModule {
	
	@Provides fun listDao(database: TodoListDatabase): ListDao = database.listDao()
	
	@Provides fun itemDao(database: TodoListDatabase): ItemDao = database.itemDao()
	
	@Provides @Singleton fun database(
		applicationContext: Context
	): TodoListDatabase = Room.databaseBuilder(
		applicationContext,
		TodoListDatabase::class.java,
		"todo-list-database"
	).build()
}

@AllOpen
@Database(entities = [DbTodoList::class, DbTodoItem::class], version = 1, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {
	
	abstract fun listDao(): ListDao
	
	abstract fun itemDao(): ItemDao
	
	suspend fun <T> runInSuspendTransaction(block: suspend () -> T) = withTransaction(block)
}