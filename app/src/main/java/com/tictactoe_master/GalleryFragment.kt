package com.tictactoe_master

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.activity.GalleryActivity
import com.tictactoe_master.activity.ui.GalleryAdapter
import com.tictactoe_master.app_data.FileDataHandler

class GalleryFragment : Fragment() {
    private var imagesList = ArrayList<Array<Int>>()
    lateinit var galleryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_gallery, container, false)

        galleryRecyclerView = myView.findViewById(R.id.gallery_RV)
        initArray()
        setRecyclerView()

        return myView
    }

    private fun initArray(){
        val p0 = FileDataHandler.readInt(activity as AppCompatActivity, "p0")
        val p1 = FileDataHandler.readInt(activity as AppCompatActivity, "p1")
        val p2 = FileDataHandler.readInt(activity as AppCompatActivity, "p2")
        val p3 = FileDataHandler.readInt(activity as AppCompatActivity, "p3")
        val p4 = FileDataHandler.readInt(activity as AppCompatActivity, "p4")

        imagesList.add(arrayOf(R.drawable.circle, R.drawable.cross, p0))
        imagesList.add(arrayOf(R.drawable.circle_green2, R.drawable.cross_green2, p1))
        imagesList.add(arrayOf(R.drawable.circle_blue, R.drawable.cross_blue, p2))
        imagesList.add(arrayOf(R.drawable.circle_purple, R.drawable.cross_purple, p3))
        imagesList.add(arrayOf(R.drawable.circle_red, R.drawable.cross_red, p4))
    }

    fun setRecyclerView() {
        val eventAdapter = GalleryAdapter(activity as GalleryActivity, this, imagesList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = eventAdapter
    }

}