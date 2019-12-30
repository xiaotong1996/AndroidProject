package com.example.androidtd.Activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.*
import com.example.androidtd.R
import com.example.androidtd.Data.Task
import com.example.androidtd.TIMER_ACTION
import com.example.androidtd.network.Api
import com.example.androidtd.util.NotificationUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity() {

    private val coroutineScope = MainScope()



    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        val textTitle=findViewById<EditText>(R.id.new_title)
        val textDescription=findViewById<EditText>(R.id.new_description)


        val btnDate = findViewById<Button>(R.id.btn_date)
        val textDate= findViewById<TextView>(R.id.in_date)

        val btnTime = findViewById<Button>(R.id.btn_time)
        val textTime=findViewById<TextView>(R.id.in_time)

        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)

        val hour=c.get(Calendar.HOUR_OF_DAY)
        val minute=c.get(Calendar.MINUTE)

        if(intent.getBooleanExtra("notification",false)){
            textDate.text=intent.getStringExtra("in_date")
            textTime.text=intent.getStringExtra("in_time")
            textTitle.setText(intent.getStringExtra("in_title"))
            textDescription.setText((intent.getStringExtra("in_description")))

        }


        btnDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                textDate.text="" + dayOfMonth + " " + month + ", " + year
            }, year, month, day)
            dpd.show()
        }

        btnTime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                textTime.text = SimpleDateFormat("HH:mm").format(c.time)
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()

        }


        val ok_btn = findViewById<Button>(R.id.OK_btn)
        ok_btn.setOnClickListener {
            val createMainActivity :Intent = Intent(applicationContext, MainActivity::class.java)
            val title = findViewById<TextView>(R.id.new_title).text.toString()
            val description = findViewById<TextView>(R.id.new_description).text.toString()


            if(textDate.text.any()&&title.any()&&description.any()&&textTime.text.any()) {

                val due_date = textDate.text.toString()+" "+textTime.text.toString()

                Log.e("test",due_date)

                coroutineScope.launch {
                    Api.INSTANCE.tasksService.createTask(
                        Task(
                            "id_$title",
                            title,
                            description,
                            due_date
                        )
                    )

                }

//                val alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
////
////                val myIntent = Intent()
////                myIntent.setAction(TIMER_ACTION)
////
////                val sender = PendingIntent.getBroadcast(this, 0, myIntent,0)
////
////
////                val triggerAtTime = System.currentTimeMillis() + 5*1000
////
////                alarmManager.set(AlarmManager.RTC_WAKEUP,triggerAtTime,sender)

                // val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
                val notiTime=c.timeInMillis - 1000*60*5

                 var mNotified = false

                if (!mNotified) {
                    val notificationUtils=NotificationUtils(notiTime,this)
                    notificationUtils.putExtras("id_$title",textTitle.text.toString(),textDescription.text.toString(),textDate.text.toString(),textTime.text.toString())
                    notificationUtils.setNotification()
                }

                startActivity(createMainActivity)

            }else{
                Toast. makeText( this, "Fill in every item please ! ", Toast.LENGTH_SHORT). show()
            }

        }
    }
}
