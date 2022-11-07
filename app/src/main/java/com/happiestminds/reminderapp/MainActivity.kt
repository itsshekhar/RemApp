package com.happiestminds.reminderapp

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Timer().schedule(700) {
            runOnUiThread {
                val allRemindersIntent = Intent(this@MainActivity, AllReminders().javaClass)
                startActivity(allRemindersIntent)
                finish()
            }
        }


    }


}