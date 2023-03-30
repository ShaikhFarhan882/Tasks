package com.example.tasks.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDAO {
    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteItem(taskEntity: TaskEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_db ORDER BY timeStamp DESC")
    fun getAllData(): LiveData<List<TaskEntity>>

    @Query("DELETE FROM task_db")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_db ORDER BY priority ASC")
    fun sort(): LiveData<List<TaskEntity>>


    @Query("SELECT * FROM task_db WHERE title LIKE :query")
    fun searchItem(query: String): LiveData<List<TaskEntity>>


}