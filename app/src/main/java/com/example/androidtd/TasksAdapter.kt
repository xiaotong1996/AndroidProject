package com.example.androidtd

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class TasksAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>(){
    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false),this)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasks[position]
        holder.itemView.findViewById<Button>(R.id.task_delete_button).setOnClickListener { onDeleteClickListener(tasks[position]) }
        holder.bind(item)
    }

    private fun onDeleteClickListener(task: Task){
        tasks.remove(task)
        notifyDataSetChanged()
    }

}