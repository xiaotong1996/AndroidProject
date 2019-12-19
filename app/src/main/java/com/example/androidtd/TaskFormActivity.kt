package com.example.androidtd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.androidtd.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class TaskFormActivity : AppCompatActivity() {

    private val coroutineScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        val ok_btn = findViewById<Button>(R.id.OK_btn)
        ok_btn.setOnClickListener {
            val createMainActivity :Intent = Intent(applicationContext, MainActivity::class.java)
            val title = findViewById<TextView>(R.id.new_title).text.toString()
            val description = findViewById<TextView>(R.id.new_description).text.toString()
            val datePicker : DatePicker = findViewById(R.id.datePicker1)

            val due_date = datePicker.dayOfMonth.toString() + "/" + (datePicker.month+1).toString()
            Log.i("qqqqqqqqqq", due_date)
            coroutineScope.launch {
                Api.tasksService.createTask(Task("id_$title", title, description))
            }
            startActivity(createMainActivity)

        }
    }
}
