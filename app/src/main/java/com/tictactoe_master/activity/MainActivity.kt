package com.tictactoe_master.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.GalleryFragment
import com.tictactoe_master.MainFragment
import com.tictactoe_master.R
import com.tictactoe_master.activity.ui.ViewPagerAdapter
import com.tictactoe_master.app_data.CoinHandler
import com.tictactoe_master.app_data.FileDataHandler

class MainActivity : AppCompatActivity() {
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar    // creating object of ToolBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the references of the declared objects above
        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)
        bar = findViewById(R.id.toolbar)

        // To make our toolbar show the application
        // we need to give it to the ActionBar
        setSupportActionBar(bar)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(MainFragment(), "Home")
        adapter.addFragment(GalleryFragment(), "Themes")
//        adapter.addFragment(LeetFragment(), "Leet Code")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)
    }
}