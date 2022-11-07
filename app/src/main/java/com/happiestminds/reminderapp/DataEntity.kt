package com.happiestminds.reminderapp

import java.sql.Time
import java.util.Date
import java.util.Timer
var allReminders= mutableListOf<DataEntity>()
data class DataEntity(var title:String, var description:String,  var date: String,var time: String,var eventId:Int) {

}