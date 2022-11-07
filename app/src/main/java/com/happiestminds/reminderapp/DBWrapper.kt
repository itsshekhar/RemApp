package com.happiestminds.reminderapp

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class DBWrapper(ctx: Context) : AppCompatActivity() {

    val helper = DBHelper(ctx)
    val db = helper.writableDatabase


    fun addStudent(rem: DataEntity): Boolean {
        val values = ContentValues()
        values.put(DBHelper.CLM_TITLE, rem.title)
        values.put(DBHelper.CLM_DESCR, rem.description)
        values.put(DBHelper.CLM_DATE, rem.date)
        values.put(DBHelper.CLM_TIME, rem.time)
        values.put(DBHelper.CLM_EVENTID, rem.eventId)


        val rowid = db.insert(DBHelper.TABLE_NAME, null, values)

        return rowid.toInt() != -1


    }

    fun getAllStudent(): Cursor {
        val clms = arrayOf(
            DBHelper.CLM_TITLE,
            DBHelper.CLM_DESCR,
            DBHelper.CLM_DATE,
            DBHelper.CLM_TIME,
            DBHelper.CLM_EVENTID
        )
        return db.query(DBHelper.TABLE_NAME, clms, null, null, null, null, null)
    }

    fun deleteStudent(title: String, eventid: Int) {
        Log.d("DBWrapper", "deleteStudent: $eventid")
        //deleteReminderOnEvent(eventid.toLong())
        db.delete(
            DBHelper.TABLE_NAME,
            "${DBHelper.CLM_TITLE}= ?",
            arrayOf(title)
        )
    }
//    fun deleteReminderOnEvent(reminderId: Long?) {
//        val reminderUri = ContentUris.withAppendedId(
//            CalendarContract.Events.CONTENT_URI,
//            151
//        )
//        val rows = contentResolver.delete(reminderUri, null, null)
}



