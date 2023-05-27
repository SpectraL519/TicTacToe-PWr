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

class MainActivity : AppCompatActivity() {

    private lateinit var oneVsOneCV: CardView
    private lateinit var oneVsBotCV: CardView
    private lateinit var oneVsOneOnlineCV: CardView
    private lateinit var shopCV: CardView
    private lateinit var accountTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    override fun onStart() {
        super.onStart()

        this.setAccountTVText()
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    private fun initView() {
        this.oneVsOneCV = findViewById(R.id.one_v_one_cv)
        this.oneVsBotCV = findViewById(R.id.one_v_bot_cv)
        this.oneVsOneOnlineCV = findViewById(R.id.one_v_one_online_cv)
        this.shopCV = findViewById(R.id.shop_cv)
        this.accountTV = findViewById(R.id.account_tv)

        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }

        this.oneVsOneCV.setOnClickListener {
            this.startGame("1_v_1")
        }

        this.oneVsBotCV.setOnClickListener {
            this.startGame("1_v_bot")
        }

        this.oneVsOneOnlineCV.setOnClickListener {
            if (Firebase.auth.currentUser == null)
                Toast.makeText(this, "First log in to play online", Toast.LENGTH_SHORT).show()
            else
                this.startGame("1_v_1_online")
        }

        this.shopCV.setOnClickListener {
            val myIntent = Intent(this, GalleryActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun startGame (gameMode: String) {
        val gameIntent = Intent(this, ChooseGameTypeActivity::class.java).apply {
            putExtra("game_mode", gameMode)
        }
        startActivity(gameIntent)
    }

}