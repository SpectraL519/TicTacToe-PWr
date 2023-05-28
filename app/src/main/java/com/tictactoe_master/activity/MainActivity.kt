package com.tictactoe_master.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.GalleryFragment
import com.tictactoe_master.MainFragment
import com.tictactoe_master.R
import com.tictactoe_master.activity.ui.ViewPagerAdapter
import com.tictactoe_master.logic.CoinHandler

class MainActivity : AppCompatActivity() {
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var accountTV: TextView
    private lateinit var coinsTV: TextView
    private var saveFileLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainFragment(), "Home")
        adapter.addFragment(GalleryFragment(), "Themes")
        pager.adapter = adapter
        tab.setupWithViewPager(pager)
        initView()
    }

    override fun onStart() {
        super.onStart()
        this.setAccountTVText()
    }

    override fun onResume() {
        super.onResume()
        coinsTV.text = String.format(
            "%s %s",
            CoinHandler.getBalance(),
            getText(R.string.currency)
        )
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    private fun initView() {
        this.accountTV = findViewById(R.id.account_tv)
        this.coinsTV = findViewById(R.id.coins_tv)
        if (!saveFileLoaded) {
            CoinHandler.loadBalance(this)
            saveFileLoaded = true
        }
        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            } else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }
    }
}
