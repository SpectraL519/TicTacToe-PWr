package com.tictactoe_master.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R
import com.tictactoe_master.app_data.CoinHandler
import com.tictactoe_master.app_data.FileDataHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}