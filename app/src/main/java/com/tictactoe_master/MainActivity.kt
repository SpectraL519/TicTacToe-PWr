package com.tictactoe_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.online_game.FirebaseCommunication

class MainActivity : AppCompatActivity() {

    private lateinit var oneVsOneCV: CardView
    private lateinit var oneVsBotCV: CardView
    private lateinit var oneVsOneOnlineCV: CardView
    private lateinit var accountTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //FirebaseApp.initializeApp(this)

        var opponentFound = false
        var waiting = false
        var playerId = Firebase.auth.currentUser!!.email!!
        var opponentId = "0"

        val database = FirebaseDatabase.getInstance()
        database.reference.child("games").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (opponentFound) {

                    if (snapshot.hasChildren()) {

                        for (game in snapshot.children) {
                            val gameId = game.key!!.toLong()
                            val playerCount = game.childrenCount.toInt()

                            if (waiting) {
                                if (playerCount == 2) {

                                }
                            }
                        }
                    }
                }
                else {

                    val gameId = System.currentTimeMillis().toString()
                    snapshot.child(gameId).child("player_id").ref.setValue(playerId)
                    waiting = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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
            // this.startGame("1_v_bot")
            Toast.makeText(this, "This functionality is not yet implemented", Toast.LENGTH_SHORT).show()
        }

        this.oneVsOneOnlineCV.setOnClickListener {
            // this.startGame("1_v_1_online")
            Toast.makeText(this, "This functionality is not yet implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGame (gameMode: String) {
        val gameIntent = Intent(this, ChooseGameTypeActivity::class.java).apply {
            putExtra("game_mode", gameMode)
        }
        startActivity(gameIntent)
    }


}