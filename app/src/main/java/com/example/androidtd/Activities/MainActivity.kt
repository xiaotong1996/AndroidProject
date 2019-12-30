package com.example.androidtd.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import androidx.core.content.edit
import com.example.androidtd.SHARED_PREF_TOKEN_KEY
import com.example.androidtd.R

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


        button_logout.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit {
                remove(SHARED_PREF_TOKEN_KEY)
            }
        }


    }


    private fun onClickListerner(){
       val intent = Intent(applicationContext, UserInfoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickAddListener()
    {
        var intent = Intent(applicationContext, TaskFormActivity::class.java)
        startActivity(intent)
    }

}
