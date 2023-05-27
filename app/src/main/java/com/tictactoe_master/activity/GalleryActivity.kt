package com.tictactoe_master.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.R
import com.tictactoe_master.activity.ui.GalleryAdapter

class GalleryActivity : AppCompatActivity(), GalleryAdapter.OnItemListener {
    private var imagesList = ArrayList<Array<Int>>()
    lateinit var galleryRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        galleryRecyclerView = findViewById(R.id.gallery_RV)
        initArray()
        setRecyclerView()
    }

    private fun initArray(){
        imagesList.add(arrayOf(R.drawable.circle, R.drawable.cross, 0))
        imagesList.add(arrayOf(R.drawable.circle_green, R.drawable.cross_green, 0))
    }

    private fun setRecyclerView() {
        val eventAdapter = GalleryAdapter(this, imagesList, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = eventAdapter
    }

    override fun onItemClick(position: Int) {
    }
}