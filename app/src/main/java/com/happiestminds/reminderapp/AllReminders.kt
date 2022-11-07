package com.happiestminds.reminderapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class AllReminders : AppCompatActivity() {
    lateinit var allRemindersList: ListView
    var remindersList = mutableListOf<DataEntity>()
    var titleList = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reminders)
        allRemindersList = findViewById(R.id.allReminderListView)



        remindersList.forEach { i -> titleList.add(i.title) }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList)

        allRemindersList.adapter = adapter

        allRemindersList.setOnItemClickListener { adapterView, view, index, l ->
            val selectedTitle = titleList[index]

            var dlg = AllReminderDialog()
            val dataBundle = Bundle()
            dlg.isCancelable = false
            var selectedData = DataEntity("", "", "", "", 0)
            remindersList.forEach { i -> if (i.title == selectedTitle) selectedData = i }
            dataBundle.putString("title", selectedTitle)
            dataBundle.putInt("eventId", selectedData.eventId)
            dataBundle.putString(
                "details",
                "Description: ${selectedData.description}\nDate: ${selectedData.date}\nTime: ${selectedData.time}"
            )

            dlg.arguments = dataBundle

            dlg.show(supportFragmentManager, null)

        }
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {

        if (checkSelfPermission(Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR),1)

        }
        super.onResume()
        setUpData()





    }

    override fun onBackPressed() {
        var dlg = MyDialog()
        val dataBundle = Bundle()
        dlg.isCancelable = false
        dataBundle.putString("msg", "confirm to exit")
        dlg.arguments = dataBundle

        dlg.show(supportFragmentManager, null)


    }

    private fun setUpData() {
        val cursor = DBWrapper(this).getAllStudent()

        val idx_title = cursor.getColumnIndexOrThrow(DBHelper.CLM_TITLE)
        val idx_descr = cursor.getColumnIndexOrThrow((DBHelper.CLM_DESCR))
        val idx_date = cursor.getColumnIndexOrThrow(DBHelper.CLM_DATE)
        val idx_time = cursor.getColumnIndexOrThrow(DBHelper.CLM_TIME)
        val idx_eventId = cursor.getColumnIndexOrThrow(DBHelper.CLM_EVENTID)


        cursor.moveToFirst()
        remindersList.clear()
        titleList.clear()

        if (cursor.count > 0) {
            do {


                val title = cursor.getString(idx_title)
                val descr = cursor.getString(idx_descr)
                val date = cursor.getString(idx_date)
                val time = cursor.getString(idx_time)
                val event = cursor.getInt(idx_eventId)


                val rem = DataEntity(title, descr, date, time, event)
                titleList.add(title)
                remindersList.add(rem)

            } while (cursor.moveToNext())



        }
    }

    fun addBtnClick(view: View) {
        val intent = Intent(this, AddReminder::class.java)
        startActivity(intent)

    }



}