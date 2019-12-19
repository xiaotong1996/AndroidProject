package com.example.androidtd

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder(itemView: View, myAdapter: TasksAdapter) : RecyclerView.ViewHolder(itemView) {


    private val myAdapter: Any

    init {
        this.myAdapter=myAdapter
    }

    fun bind(task: Task){
        val itemText=itemView.findViewById<TextView>(R.id.task_title)
        itemText.text=task.title
        val itemDescription=itemView.findViewById<TextView>(R.id.task_description)
        itemDescription.text=task.description
//        val duetime = itemView.findViewById<TextView>(R.id.due_time)
//        duetime.text = "Date:  "+task.due_date
    }



}