package com.tictactoe_master.activity

import android.app.ProgressDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R

class OnlineGameActivity : GameActivity() {

    private var databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tictactoe-master-9efa8-default-rtdb.firebaseio.com/")

    private var playerUniqueId = "0"
    private val playerName = Firebase.auth.currentUser!!.email!!
    private var opponentUniqueId = "0"
    private var opponentName = ""
    private var connectionId = ""

    private var playerTurn = ""

    private lateinit var movesEventListener: ValueEventListener

    private lateinit var opponentNameTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initConnection()
    }

    override fun initView() {
        super.initView()
        this.opponentNameTV = findViewById(R.id.opponent_name_tv)
    }

    private fun encodeGameParams(): String {
        var params = ""

        params += intent.getIntExtra("size", 3).toString()
        params += intent.getStringExtra("win_cond")?.get(0) ?: "c"
        params += intent.getStringExtra("game_type")?.get(0) ?: "c"
        if (intent.getStringExtra("game_type") == "point")
            params += intent.getIntExtra("points_to_win", 2).toString()

        return params
    }

    override fun cellClick(textView: TextView, x: Int, y: Int) {
        // TODO:
    }

    private fun initConnection() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Waiting for opponent")
        progressDialog.setCancelable(false)
        val colorDrawable = ColorDrawable(ContextCompat.getColor(this, R.color.bg_color))
        progressDialog.window?.setBackgroundDrawable(colorDrawable)
        progressDialog.show()

        this.playerUniqueId = System.currentTimeMillis().toString()

        this.databaseReference.child("connections").addValueEventListener(object: ValueEventListener {

            private var opponentFound = false
            private var createdGame = false

            private fun createGame(snapshot: DataSnapshot) {
                this.createdGame = true

                val connectionUniqueId = System.currentTimeMillis().toString()
                snapshot.child(connectionUniqueId)
                    .child("params")
                    .ref.setValue(this@OnlineGameActivity.encodeGameParams())

                snapshot.child(connectionUniqueId)
                    .child(this@OnlineGameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(this@OnlineGameActivity.playerName)
            }

            private fun addOpponent(connId: String, connection: DataSnapshot) {

                if (connection.child("params").value.toString() != this@OnlineGameActivity.encodeGameParams())
                    return

                this@OnlineGameActivity.playerTurn = this@OnlineGameActivity.playerUniqueId
                // TODO: apply player turn

                for (player in connection.children) {
                    if (player.key != "params" && player.key != this@OnlineGameActivity.playerUniqueId) {
                        this.opponentFound = true

                        this@OnlineGameActivity.opponentName = player.child("player_name").value as String
                        this@OnlineGameActivity.opponentUniqueId = player.key.toString()
                        this@OnlineGameActivity.connectionId = connId

                        this@OnlineGameActivity.databaseReference.child("moves").child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.movesEventListener)

                        progressDialog.hide()

                        this@OnlineGameActivity.opponentNameTV.text = "Playing with ${this@OnlineGameActivity.opponentName}"

                        this@OnlineGameActivity.databaseReference.child("connections").removeEventListener(this)
                        break
                    }
                }
            }

            private fun joinGame(connId: String, connection: DataSnapshot) {

                if (connection.child("params").value.toString() != this@OnlineGameActivity.encodeGameParams())
                    return

                connection
                    .child(this@OnlineGameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(this@OnlineGameActivity.playerName)

                for (player in connection.children) {

                    if (player.key != "params" && player.key != this@OnlineGameActivity.playerUniqueId) {
                        this.opponentFound = true

                        this@OnlineGameActivity.opponentName = player.child("player_name").value as String
                        this@OnlineGameActivity.opponentUniqueId = player.key.toString()

                        this@OnlineGameActivity.playerTurn = this@OnlineGameActivity.opponentUniqueId
                        // TODO: apply player turn

                        this@OnlineGameActivity.connectionId = connId

                        this@OnlineGameActivity.databaseReference.child("moves").child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.movesEventListener)

                        progressDialog.hide()

                        this@OnlineGameActivity.opponentNameTV.text = "Playing with ${this@OnlineGameActivity.opponentName}"

                        this@OnlineGameActivity.databaseReference.child("connections").removeEventListener(this)
                        break
                    }

                }
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (this.opponentFound)
                    return

                if (snapshot.hasChildren()) {

                    for (connection in snapshot.children) {

                        val connId = connection.key!!
                        val getPlayersCount = connection.childrenCount.toInt() - 1

                        if (this.createdGame && getPlayersCount == 2) {
                            this.addOpponent(connId, connection)
                        }
                        else if (!this.createdGame && getPlayersCount == 1) {
                            this.joinGame(connId, connection)
                        }

                    }

                    if (!this.opponentFound && !this.createdGame) {
                        this.createGame(snapshot)
                    }

                }
                else if (!this.createdGame) {
                    this.createGame(snapshot)
                }

            }

            override fun onCancelled(error: DatabaseError) {}

        })

        this.movesEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // TODO: handle moves

            }

            override fun onCancelled(error: DatabaseError) {}
        }

    }

}