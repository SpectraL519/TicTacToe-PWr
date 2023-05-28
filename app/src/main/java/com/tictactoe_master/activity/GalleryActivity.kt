package com.tictactoe_master.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.R
import com.tictactoe_master.activity.ui.GalleryAdapter
import com.tictactoe_master.app_data.FileDataHandler

class GalleryActivity : AppCompatActivity() {
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
        val p0 = FileDataHandler.readInt(this, "p0")
        val p1 = FileDataHandler.readInt(this, "p1")
        val p2 = FileDataHandler.readInt(this, "p2")
        val p3 = FileDataHandler.readInt(this, "p3")
        val p4 = FileDataHandler.readInt(this, "p4")

        imagesList.add(arrayOf(R.drawable.circle, R.drawable.cross, p0))
        imagesList.add(arrayOf(R.drawable.circle_green, R.drawable.cross_green, p1))
        imagesList.add(arrayOf(R.drawable.circle_blue, R.drawable.cross_blue, p2))
        imagesList.add(arrayOf(R.drawable.circle_purple, R.drawable.cross_purple, p3))
        imagesList.add(arrayOf(R.drawable.circle_red, R.drawable.cross_red, p4))
    }

    fun setRecyclerView() {
        val eventAdapter = GalleryAdapter(this, imagesList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = eventAdapter
    }
}