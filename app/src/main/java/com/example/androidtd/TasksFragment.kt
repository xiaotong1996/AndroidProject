package com.example.androidtd

import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class TasksFragment : Fragment(){

//    private val tasks1 = arrayOf(
//        Task(id = "id_1", title = "Task 1", description = "description 1"),
//        Task(id = "id_2", title = "Task 2"),
//        Task(id = "id_3", title = "Task 3")
//    )
//    private val mutableList = tasks1.toMutableList()

    private val tasksRepository = TasksRepository()
    private val tasks = mutableListOf<Task>()
    private val tasksAdapter= TasksAdapter(tasks)

    private val coroutineScope = MainScope()


    private val taskViewModel by lazy{
        ViewModelProviders.of(this).get(TasksViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.taskslist_fragment,container) as View
        val recycleListView:RecyclerView= view.findViewById(R.id.tasks_recycler_view)


        //val adapter = TasksAdapter(mutableList)

        recycleListView.layoutManager=LinearLayoutManager(this.context)
        recycleListView.adapter=tasksAdapter


        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tasksRepository.getTasks().observe(this, Observer {
            if (it != null) {
                Log.e("it",it.toString())
                tasks.clear()
                tasks.addAll(it)
                tasksAdapter.notifyDataSetChanged()
                Log.e("tasksaaaaa",tasks.toString())
            }
        })
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        coroutineScope.launch {
            Api.tasksService.getTasks()
        }
        taskViewModel.loadTasks(this)
        super.onResume()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}