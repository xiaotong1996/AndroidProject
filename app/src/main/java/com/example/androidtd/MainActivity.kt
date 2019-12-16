package com.example.androidtd

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.example.androidtd.receiver.AlarmReceiver
import com.example.androidtd.util.cancelNotifications
import com.example.androidtd.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_fragment.*

class MainActivity : AppCompatActivity() {

//    private val notifyIntent = Intent(application, AlarmReceiver::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        header_avatar.setOnClickListener{onClickListerner()}
        add_task_btn.setOnClickListener { onClickAddListener() }

        val headText: TextView = findViewById(R.id.header_text)
        headText.text="Hello World"


        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )

    }


    private fun onClickListerner(){
        val intent = Intent(applicationContext, UserInfoActivity::class.java)


        val notificationManager =
            ContextCompat.getSystemService(
                application,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.sendNotification("hello", applicationContext)


        startActivity(intent)

    }

    private fun onClickAddListener()
    {
        var intent = Intent(applicationContext, TaskFormActivity::class.java)
        startActivity(intent)
    }


    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW

            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_description)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }

}
