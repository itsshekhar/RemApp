package com.happiestminds.reminderapp

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val message=arguments?.getString("msg")

        var dlg: Dialog?=null
        //create dialog here
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("configuration")
            builder.setMessage(message)
            builder.setPositiveButton("YES"){
                //its an  onClick Listner...on clicking button
                    dialog, i->activity?.finish()
            }
            builder.setNegativeButton("No"){
                //its an  onClick Listner...on clicking No button
                    dialog,i->dialog.cancel()
            }

            dlg=builder.create()
        }


        return dlg!!
    }
}