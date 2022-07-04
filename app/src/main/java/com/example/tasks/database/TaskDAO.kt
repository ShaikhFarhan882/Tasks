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
    fun getAllData() : LiveData<List<TaskEntity>>

    @Query("DELETE FROM task_db")
     suspend fun deleteAll()
}