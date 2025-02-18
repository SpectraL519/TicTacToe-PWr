package com.tictactoe_master.activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.R
import com.tictactoe_master.activity.ui.GalleryAdapter
import com.tictactoe_master.app_data.FileDataHandler

class GalleryFragment : Fragment() {
    private var imagesList = ArrayList<Array<Int>>()
    private lateinit var galleryRecyclerView: RecyclerView

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

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun initArray(){
        val p0 = FileDataHandler.readInt(activity as AppCompatActivity, "p0")
        val p1 = FileDataHandler.readInt(activity as AppCompatActivity, "p1")
        val p2 = FileDataHandler.readInt(activity as AppCompatActivity, "p2")
        val p3 = FileDataHandler.readInt(activity as AppCompatActivity, "p3")
        val p4 = FileDataHandler.readInt(activity as AppCompatActivity, "p4")
        val p5 = FileDataHandler.readInt(activity as AppCompatActivity, "p5")
        val p6 = FileDataHandler.readInt(activity as AppCompatActivity, "p6")
        val p7 = FileDataHandler.readInt(activity as AppCompatActivity, "p7")
        val p8 = FileDataHandler.readInt(activity as AppCompatActivity, "p8")

        imagesList.add(arrayOf(R.drawable.circle, R.drawable.cross, p0))
        imagesList.add(arrayOf(R.drawable.circle_green2, R.drawable.cross_green2, p1))
        imagesList.add(arrayOf(R.drawable.circle_blue, R.drawable.cross_blue, p2))
        imagesList.add(arrayOf(R.drawable.circle_purple, R.drawable.cross_purple, p3))
        imagesList.add(arrayOf(R.drawable.circle_red, R.drawable.cross_red, p4))
        imagesList.add(arrayOf(R.drawable.sea1, R.drawable.sea2, p5))
        imagesList.add(arrayOf(R.drawable.mobile1, R.drawable.mobile2, p6))
        imagesList.add(arrayOf(R.drawable.cat1, R.drawable.cat2, p7))
        imagesList.add(arrayOf(R.drawable.food1, R.drawable.food2, p8))
    }

    fun setRecyclerView() {
        val eventAdapter = GalleryAdapter(activity as AppCompatActivity, this, imagesList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        galleryRecyclerView.layoutManager = layoutManager
        galleryRecyclerView.adapter = eventAdapter
    }

}