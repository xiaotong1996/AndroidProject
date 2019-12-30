package com.example.androidtd.util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils(timeInMilliSeconds: Long, activity: Activity) {

    val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver

    val timeInMilliSeconds=timeInMilliSeconds
    val activity=activity


    fun putExtras(id:String,title : String,description: String,date : String,time:String){
        alarmIntent.putExtra("in_ID",id)
        alarmIntent.putExtra("in_title",title)
        alarmIntent.putExtra("in_description",description)
        alarmIntent.putExtra("in_date",date)
        alarmIntent.putExtra("in_time",time)

    }
    fun setNotification() {

        //------------  alarm settings start  -----------------//

        if (timeInMilliSeconds > 0) {



            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)


            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds


            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }

        //------------ end of alarm settings  -----------------//


    }
}