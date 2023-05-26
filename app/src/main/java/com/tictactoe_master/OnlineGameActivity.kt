package com.tictactoe_master

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class OnlineGameActivity : GameActivity() {

    private var databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tictactoe-master-9efa8-default-rtdb.firebaseio.com/")

    private var playerUniqueId = "0"
    private var playerName = Firebase.auth.currentUser!!.email!!
    private var opponentUniqueId = "0"
    private var opponentName = ""
    private var connectionId = ""

    private var playerTurn = ""

    private lateinit var turnsEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initConnection()
    }

    private fun initConnection() {

        this.playerUniqueId = System.currentTimeMillis().toString()

        this.databaseReference.child("connections").addValueEventListener(object:
            ValueEventListener {

            private var opponentFound = false
            private var createdGame = false

            private fun createGame(snapshot: DataSnapshot) {
                this.createdGame = true

                val connectionUniqueId = System.currentTimeMillis().toString()
                snapshot.child(connectionUniqueId)
                    .child(this@OnlineGameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(Firebase.auth.currentUser!!.email)
            }

            private fun addOpponent(connId: String, connection: DataSnapshot) {

                this@OnlineGameActivity.playerTurn = this@OnlineGameActivity.playerUniqueId
                // TODO: apply player turn

                for (player in connection.children) {
                    if (player.key != this@OnlineGameActivity.playerUniqueId) {
                        this.opponentFound = true

                        this@OnlineGameActivity.opponentName = player.child("player_name").value as String
                        this@OnlineGameActivity.opponentUniqueId = player.key.toString()
                        this@OnlineGameActivity.connectionId = connId

                        this@OnlineGameActivity.databaseReference.child("turns").child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.turnsEventListener)

                        // TODO: hide ProcessDialog

                        this@OnlineGameActivity.databaseReference.child("connections").removeEventListener(this)
                    }
                }
            }

            private fun joinGame(connId: String, connection: DataSnapshot) {
                connection
                    .child(this@OnlineGameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(Firebase.auth.currentUser!!.email)

                for (player in connection.children) {

                    if (player.key != this@OnlineGameActivity.playerUniqueId) {
                        this.opponentFound = true

                        this@OnlineGameActivity.opponentName = player.child("player_name").value as String
                        this@OnlineGameActivity.opponentUniqueId = player.key.toString()

                        this@OnlineGameActivity.playerTurn = this@OnlineGameActivity.opponentUniqueId
                        // TODO: apply player turn

                        this@OnlineGameActivity.connectionId = connId

                        this@OnlineGameActivity.databaseReference.child("turns").child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.turnsEventListener)

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
                        val getPlayersCount = connection.childrenCount.toInt()

                        if (this.createdGame && getPlayersCount == 2) {
                            this.addOpponent(connId, connection)
                            return
                        }
                        else if (!this.createdGame && getPlayersCount == 1) {
                            this.joinGame(connId, connection)
                            return
                        }

                    }

                    if (!this.createdGame) {
                        this.createGame(snapshot)
                    }

                }
                else if (!this.createdGame) {
                    this.createGame(snapshot)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        this.turnsEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dataSnapshot in snapshot.children) {

                    if (dataSnapshot.childrenCount.toInt() == 2) {

                        val boxPosition = dataSnapshot.child("box_position").value.toString().toInt()
                        val getPlayerId = dataSnapshot.child("player_id").value.toString()

                        // TODO: handle selected box

                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

    }

}