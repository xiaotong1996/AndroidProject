package com.example.androidtd

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {
    private val coroutineScope = MainScope()

    private val repository = TasksRepository()

    private val tasks = mutableListOf<Task>()
    private val tasksAdapter = TasksAdapter(tasks)
//    val tasksAdapter
fun loadTasks(lifecycle: LifecycleOwner) {
    repository.getTasks().observe(
        lifecycle,
        Observer {
            if(it==null)return@Observer
            tasks.clear()
            tasks.addAll(it)
            tasksAdapter.notifyDataSetChanged()

        })
}
}

