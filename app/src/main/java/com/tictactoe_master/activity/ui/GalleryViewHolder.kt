package com.tictactoe_master.activity.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.R

class GalleryViewHolder(
    itemView: View,
    onItemListener: GalleryAdapter.OnItemListener
): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val img1: ImageView
    val img2: ImageView
    val selectTV: TextView
    val selectCV: CardView

    private val onItemListener: GalleryAdapter.OnItemListener

    init {
        img1 = itemView.findViewById(R.id.preview1_iv)
        img2 = itemView.findViewById(R.id.preview2_iv)
        selectTV = itemView.findViewById(R.id.select_TV)
        selectCV = itemView.findViewById(R.id.select_CV)

        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition)
//            onItemListener.onItemClick(adapterPosition, eventName.text as String)
    }
}