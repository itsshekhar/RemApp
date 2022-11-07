package com.happiestminds.reminderapp

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class AddReminder : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    lateinit var titleText: TextView
    lateinit var descriptionText: TextView
    lateinit var timeTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var dateButton: Button
    lateinit var timeButton: Button


//
//    private val contactResult = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//
//        if (it.resultCode == RESULT_OK) {
//            //grab data if success
//            val selectedContactUri = it.data?.dataString
//
//            retrieveContactDetails(it.data?.data!!)
//
//        } else if (it.resultCode == RESULT_CANCELED) {
//
//
//
//            Toast.makeText(this, "No Contact Selected", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        titleText = findViewById(R.id.titleEditText)
        descriptionText = findViewById(R.id.descriptionEditText)
        timeTextView = findViewById(R.id.timeTextView)
        dateTextView = findViewById(R.id.datetextView)
        dateButton = findViewById(R.id.dateButton)
        timeButton = findViewById(R.id.timeButton)


    }


    fun selectDate(view: View) {
        val dlg = DatePickerDialog(this)
        dlg.setOnDateSetListener { dPicker, year, month, day ->
            dateTextView.text = "$day-${month + 1}-$year"


        }

        dlg.show()
    }

    fun selectTime(view: View) {

        val dlg = TimePickerDialog(this, this, 10, 0, true)

        dlg.show()

    }


    override fun onTimeSet(tPicker: TimePicker?, hh: Int, mm: Int) {
        timeTextView.text = "$hh:$mm"

        // timeButton.isEnabled = false
    }






    fun calenderBtnClick(view: View) {
        if (titleText.text.toString().isNotEmpty() && timeTextView.text.toString().isNotEmpty() &&
            dateTextView.text.toString().isNotEmpty()
        ) {


            var dataReceived = DataEntity(
                titleText.text.toString(),
                descriptionText.text.toString(),
                timeTextView.text.toString(),
                dateTextView.text.toString(),0
            )


           // allReminders.add(dataReceived)
            val dateString = "${dateTextView.text.toString()} ${timeTextView.text.toString()}"

            Log.d("Add reminder", "Date: $dateString")

            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            val date = format.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            Log.d("Add reminder", "milli: ${cal.timeInMillis}")
//            val startMillis: Long = Calendar.getInstance().run {
//                set(
//                    dateTextView.text.toString().split("-")[2].toInt(),
//                    dateTextView.text.toString().split("-")[1].toInt(),
//                    dateTextView.text.toString().split("-")[0].toInt(),
//                    timeTextView.text.toString().split(":")[0].toInt(),
//                    timeTextView.text.toString().split(":")[1].toInt()
//                )
//                timeInMillis
//            }
//            val endMillis: Long = Calendar.getInstance().run {
//                set(
//                    dateTextView.text.toString().split("-")[2].toInt(),
//                    dateTextView.text.toString().split("-")[1].toInt(),
//                    dateTextView.text.toString().split("-")[0].toInt(),
//                    (timeTextView.text.toString().split(":")[0] + 1).toInt(),
//                    timeTextView.text.toString().split(":")[1].toInt()
//                )
//                timeInMillis
//            }

            //---------------------------------------------------------------------------------


            var value = ContentValues();
//                value.put(Events._ID, 1);
            // value.put("attendeeEmail", "sdfsf@gmail.com");
//
            value.put(Events.DTSTART, cal.timeInMillis)
            value.put(Events.DTEND, cal.timeInMillis + 60 * 1000);
            value.put(Events.TITLE, titleText.text.toString());
            value.put(Events.DESCRIPTION,descriptionText.text.toString());
            value.put(Events.CALENDAR_ID, 1);
            value.put(Events.EVENT_TIMEZONE, "IST")
            value.put(Events.HAS_ALARM, 10)


//            var id1: Int
//            var alarmid: Int
//            var attendeesUri = Uri.parse("content://com.android.calendar/attendees")

            var uri1 = contentResolver.insert(Events.CONTENT_URI, value);
            Log.d("Add Reminder", "calenderBtnClick:  $uri1")
            val evenID = uri1?.lastPathSegment?.toInt()
            if (evenID != null) {
                dataReceived.eventId = evenID
                if (DBWrapper(this).addStudent(dataReceived)) {
                    Toast.makeText(this, "Student details added $dataReceived", Toast.LENGTH_LONG)
                        .show()

                } else {
                    Toast.makeText(this, "Student details Not Added", Toast.LENGTH_LONG).show()

                }


            }
            //reminder insert
            val cr: ContentResolver = contentResolver
            //  var  REMINDERS_URI = Uri.parse( "content://com.android.calendar/"+ "reminders");
            var values = ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, evenID);
            values.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_DEFAULT
            );
            values.put(
                CalendarContract.Reminders.MINUTES,
                CalendarContract.Reminders.MINUTES_DEFAULT
            );
            val reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);

            val allRemindersIntent = Intent(this, AllReminders().javaClass)
            startActivity(allRemindersIntent)


            Log.d("Add reminder", "reminder uri: $reminderUri")
            finish()

            //--------------------------------------------------------------------------------


//            val intent = Intent(Intent.ACTION_INSERT)
//                .setData(CalendarContract.Events.CONTENT_URI)
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
//                .putExtra(CalendarContract.Events.TITLE, titleText.text.toString())
//                .putExtra(CalendarContract.Events.DESCRIPTION, descriptionText.text.toString())
//                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
//                .putExtra(
//                    CalendarContract.Events.AVAILABILITY,
//                    CalendarContract.Events.AVAILABILITY_BUSY
//                )
//                .putExtra(Intent.EXTRA_EMAIL, "rohit@gmail.com,Sri@gamil.com")
//            startActivity(intent)
        } else {
            if (titleText.text.isEmpty()) titleText.error = "Enter title"
            if (timeTextView.text.isEmpty()) timeTextView.error = "Select Time"
            if (dateTextView.text.isEmpty()) dateTextView.error = "Select Date"
        }
    }

    fun allReminderCall(view: View) {
        val allRemindersIntent = Intent(this, AllReminders().javaClass)
        startActivity(allRemindersIntent)

    }


//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode==1){
//            if(permissions.isNotEmpty()){
//                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    Log.d("MainActivity", "onRequestPermissionsResult: granted")
//
//                }else{
//
//                    Log.d("MainActivity", "onRequestPermissionsResult: granted")
//                }
//            }
//        }
//    }
//
//    private fun retrieveContactDetails(selectionUri: Uri) {
//        //name and column
//        //1 from uri we need to get row id(contact id)
//        val contactId = selectionUri.lastPathSegment
//        Log.d("MainActivity", "contact id : $contactId ")
//
//        //2 for that contactid-query PHONE table of contact db
//        val cursor = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null,
//            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID}=?",
//            arrayOf(contactId),
//            null
//        )
//        Log.d("Main Activity", "row Count : ${cursor?.count} ")
//
//        //get phone number from query uri
//        val idx_name = cursor?.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//        val idx_number = cursor?.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
//        if (cursor?.moveToFirst() == true) {
//            do {
////                contactName = cursor.getString(idx_name!!)
////                numberList.add((cursor.getString(idx_number!!)))
//
//
//            } while (cursor.moveToNext())
//            Log.d("MainActivity", "phone Number : numberList")
//
//        } else {
//            Toast.makeText(this, "Select contact doesnot have phone number", Toast.LENGTH_SHORT)
//                .show()
//
//        }
//
//
//    }


//    fun viewClick(view: View) {
//        if (titleText.text.toString().isNotEmpty() && timeTextView.text.toString().isNotEmpty() &&
//            dateTextView.text.toString().isNotEmpty()
//        ) {
//
//            var dataReceived = DataEntity(
//                titleText.text.toString(),
//                descriptionText.text.toString(),
//                dateTextView.text.toString(),
//                timeTextView.text.toString(),0
//
//            )
//
//
//            if (DBWrapper(this).addStudent(dataReceived)) {
//                Toast.makeText(this, "Student details added $dataReceived", Toast.LENGTH_LONG)
//                    .show()
//
//            } else {
//                Toast.makeText(this, "Student details Not Added", Toast.LENGTH_LONG).show()
//
//            }
//
//
//
//            //var allRemindersI=Intent(,AllReminders::javaClass)
//
//        } else {
//            if (titleText.text.isEmpty()) titleText.error = "Enter title"
//            if (timeTextView.text.isEmpty()) timeTextView.error = "Select Time"
//            if (dateTextView.text.isEmpty()) dateTextView.error = "Select Date"
//
//        }
//
//
//    }


}