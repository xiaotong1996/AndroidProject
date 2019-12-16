package com.example.androidtd

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {
    private val coroutineScope = MainScope()

//    private val repository
//    private val tasks
//    val tasksAdapter
    fun loadTasks() {
        coroutineScope.launch {
        Api.tasksService.getTasks()
    }
    }
}

