package com.example.tasks.repository

import androidx.lifecycle.LiveData
import com.example.tasks.database.TaskDAO
import com.example.tasks.database.TaskEntity

class TaskRepository(val taskDAO: TaskDAO) {

    suspend fun insertData(taskEntity: TaskEntity) = taskDAO.insert(taskEntity)

    suspend fun updateData(taskEntity: TaskEntity) = taskDAO.update(taskEntity)

    suspend fun deleteItem(taskEntity: TaskEntity) = taskDAO.deleteItem(taskEntity)

    suspend fun deleteAll(){
        taskDAO.deleteAll()
    }

    fun getAllTask() : LiveData<List<TaskEntity>> = taskDAO.getAllData()
}