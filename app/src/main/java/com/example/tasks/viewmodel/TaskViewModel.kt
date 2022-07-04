package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasks.database.TaskDatabase
import com.example.tasks.database.TaskEntity
import com.example.tasks.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repository: TaskRepository

    val getAllTasks: LiveData<List<TaskEntity>>

    init {
        repository = TaskRepository(taskDao)
        getAllTasks = repository.getAllTask()
    }

    fun insert(taskEntity: TaskEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertData(taskEntity)
            }
        }
    }

    fun update(taskEntity: TaskEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateData(taskEntity)
            }
        }
    }

    fun delete(taskEntity: TaskEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteItem(taskEntity)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteAll()
            }
        }
    }


}