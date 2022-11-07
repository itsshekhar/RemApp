package com.happiestminds.reminderapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "reminder.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //create table (schema)
        //oncreate executed only once
        db?.execSQL(TABLE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //executed when version mismatch
        //add tables ,drop table,modify schema of existing tables

    }

    companion object {
        const val TABLE_NAME = "reminder"
        const val CLM_TITLE = "title"
        const val CLM_DESCR = "description"
        const val CLM_TIME = "time"
        const val CLM_DATE = "date"
        const val CLM_EVENTID ="eventId"
        private const val TABLE_QUERY =
            "CREATE TABLE $TABLE_NAME ( $CLM_TITLE TEXT ,$CLM_DESCR TEXT,$CLM_DATE TEXT,$CLM_TIME TEXT,$CLM_EVENTID NUMBER )"
    }

}