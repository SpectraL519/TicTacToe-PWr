package com.tictactoe_master.activity.ui

import android.annotation.SuppressLint
import android.app.ActionBar
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.R

class GalleryAdapter(
    private val sourceList: ArrayList<Array<Int>>,
    private val onItemListener: OnItemListener
    ): RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cell_gallery, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = ActionBar.LayoutParams.WRAP_CONTENT
        layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
//        Log.v("events size", sourceList.size.toString())

        return GalleryViewHolder(view, onItemListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.img1.setImageResource(sourceList[position][0])
        holder.img2.setImageResource(sourceList[position][1])
        val price = sourceList[position][2]
        if (price != 0){
            holder.selectTV.text = "$price \uD83E\uDE99"
        }else{
            holder.selectTV.text = "select"
        }
//        holder.rating.rating = ratingList[position]
//        holder.eventName.text = eventName[position]
//        holder.eventTime.text = eventTime[position]
//        holder.eventDesc.text = eventDesc[position]
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
//        fun onItemClick(position: Int, dayText: String?)
    }
}