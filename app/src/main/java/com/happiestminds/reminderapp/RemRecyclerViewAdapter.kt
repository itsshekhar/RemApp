package com.happiestminds.reminderapp

import android.media.Image
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RemRecyclerViewAdapter(
    private val values: List<DataEntity>
) : RecyclerView.Adapter<RemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_rem,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = values[position]
//        holder.idView.text = item.id
//        holder.contentView.text = item.content
        holder.titleV.text=data.title
        holder.dateV.text=data.date
        holder.timeV.text=data.time
        holder.descriptionT.text=data.description


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }

        val imageV:ImageView=view.findViewById(R.id.imgV)
        val titleV:TextView=view.findViewById(R.id.titleV)
        val descriptionT:TextView=view.findViewById(R.id.descV)
        val timeV:TextView=view.findViewById(R.id.timeT)
        val dateV:TextView=view.findViewById(R.id.dateT)
    }

}