package com.example.androidtd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TasksRepository {
    private val tasksService = Api.tasksService
    private val coroutineScope = MainScope()

    fun getTasks(): LiveData<List<Task>?> {
        val tasks = MutableLiveData<List<Task>?>()
        coroutineScope.launch { tasks.postValue(loadTasks()) }
        return tasks
    }

    private suspend fun loadTasks(): List<Task>? {
        val tasksResponse = tasksService.getTasks()
        Log.e("loadTasks", tasksResponse.body().toString())
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    suspend fun deleteTask(id: String): Boolean {
        val tasksResponse = tasksService.deleteTask(id)
        return tasksResponse.isSuccessful
    }
}