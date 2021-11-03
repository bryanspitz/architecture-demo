package com.bryanspitz.architecturedemo.repository.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
	
	@Query(
		"""
        SELECT list.*, COUNT(item.id) AS itemCount
        FROM list LEFT JOIN item ON list.id = item.listId
        GROUP BY list.id
    """
	)
	@MapInfo(valueColumn = "itemCount")
	fun getLists(): Flow<Map<DbTodoList, Int>>
	
	@Query("SELECT * FROM list WHERE list.id = :id")
	fun getList(id: Long): Flow<DbTodoList>
	
	@Insert suspend fun insert(list: DbTodoList)
	
	@Update suspend fun update(list: DbTodoList)
	
	@Delete suspend fun delete(list: DbTodoList)
}