package com.bryanspitz.architecturedemo.repository.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
	
	@Query(
		"""
        SELECT * FROM item
        WHERE listId = :listId
    """
	)
	fun getListItems(listId: Long): Flow<List<DbTodoItem>>
	
	@Insert suspend fun insert(item: DbTodoItem)
	
	@Update suspend fun update(item: DbTodoItem)
	
	@Delete suspend fun delete(item: DbTodoItem)
	
	@Query("DELETE FROM item WHERE id = :listId")
	suspend fun deleteAll(listId: Long)
}