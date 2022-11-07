package com.happiestminds.reminderapp


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AllReminderDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val selectedTitle = arguments?.getString("title")
        val selectedEventId=arguments?.getInt("eventId")
        var details=arguments?.getString("details")



        var dlg: Dialog? = null
        //create dialog here
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Title: $selectedTitle")
            builder.setMessage(details)
            builder.setPositiveButton("OK") {
                //its an  onClick Listner...on clicking button
                    dialog, i ->
                dialog.cancel()
            }
            builder.setNegativeButton("delete") {

                //its an  onClick Listner...on clicking No button

                dialog,i->
               DBWrapper(requireContext()).deleteStudent(selectedTitle!!,selectedEventId!!)

                dialog.cancel()
                var intent= Intent(requireContext(),AllReminders::class.java)
                activity?.finish()
                startActivity(intent)


            }


            dlg = builder.create()
        }


        return dlg!!
    }
}