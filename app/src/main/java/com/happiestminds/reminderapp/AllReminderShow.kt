package com.happiestminds.reminderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllReminderShow : AppCompatActivity() {
    lateinit var rView:RecyclerView
    var listOfReminders= mutableListOf<DataEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reminder_show)
        rView = findViewById(R.id.rView)

        rView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rView.adapter = RemRecyclerViewAdapter(listOfReminders)



    }
}