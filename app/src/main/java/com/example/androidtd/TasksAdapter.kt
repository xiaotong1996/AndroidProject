package com.example.androidtd

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtd.Data.Task
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class TasksAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>(){
    override fun getItemCount(): Int {
        return tasks.size
    }
    private val coroutineScope = MainScope()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false),this)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasks[position]
        Log.e("eeeeeeeee " ," "+position+" "+item.due_date)
        holder.itemView.findViewById<Button>(R.id.task_delete_button).setOnClickListener { onDeleteClickListener(tasks[position]) }
        holder.bind(item)

    }

    private fun onDeleteClickListener(task: Task){
        coroutineScope.launch {
            Api.INSTANCE.tasksService.deleteTask(task.id)
        }

        tasks.remove(task)
        notifyDataSetChanged()
    }

}