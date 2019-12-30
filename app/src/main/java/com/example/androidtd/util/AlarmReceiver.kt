package com.example.androidtd.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.androidtd.Activities.TaskFormActivity
import com.example.androidtd.R
import com.example.androidtd.TIMER_ACTION

class AlarmReceiver :BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {
//        val notification= context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (intent != null) {
//            if(intent.action.equals(TIMER_ACTION)){
//
//                Log.e("notification","1111111111111111")
//                val intent=Intent(context,TaskFormActivity::class.java)
//                val pendingIntent=PendingIntent.getActivity(context,0,intent,0)
//                val builder=NotificationCompat.Builder(context)
//                    .setSmallIcon(R.drawable.ic_stat_name)
//                    .setContentTitle("Ding")
//                    .setContentText("You have a task to do!")
//                    .setPriority((NotificationCompat.PRIORITY_DEFAULT))
//                    .setContentIntent(pendingIntent)
//                    .build()
//                notification.notify(1,builder)
//
//
//
//            }
//        }
        val service = Intent(context, NotificationService::class.java)
        if (intent != null) {
            service.putExtra("reason", intent.getStringExtra("reason"))
            service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
            service.putExtra("in_title",intent.getStringExtra("in_title"))
            service.putExtra("in_description",intent.getStringExtra("in_description"))
            service.putExtra("in_date",intent.getStringExtra("in_date"))
            service.putExtra("in_time",intent.getStringExtra("in_time"))
            service.putExtra("in_id",intent.getStringExtra("in_id"))

        }


        if (context != null) {
            context.startService(service)
        }

    }
}