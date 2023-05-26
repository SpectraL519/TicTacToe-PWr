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
    private var opponentFound = false
    private var opponentUniqueId = "0"
    private var createdGame = false
    private var playerTurn = ""
    private var connectionId = ""

    private lateinit var turnsEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initConnection()
    }

    private fun initConnection() {

        this.playerUniqueId = System.currentTimeMillis().toString()

        this.databaseReference.child("connections").addValueEventListener(object:
            ValueEventListener {

            private fun createGame(snapshot: DataSnapshot) {
                val connectionUniqueId = System.currentTimeMillis().toString()
                snapshot.child(connectionUniqueId)
                    .child(playerUniqueId)
                    .child("player_name")
                    .ref.setValue(Firebase.auth.currentUser!!.email)

                createdGame = true
            }

            private fun addOpponent(connId: String, connection: DataSnapshot) {

                playerTurn = playerUniqueId
                // TODO: apply player turn

                for (player in connection.children) {
                    if (player.key != playerUniqueId) {
                        var getOpponentName = player.child("player_name").value as String
                        Toast.makeText(this@OnlineGameActivity, getOpponentName, Toast.LENGTH_SHORT).show()
                        opponentUniqueId = player.key.toString()
                        connectionId = connId
                        opponentFound = true

                        databaseReference.child("turns").child(connectionId)
                            .addValueEventListener(turnsEventListener)

                        // TODO: hide ProcessDialog

                        databaseReference.child("connections").removeEventListener(this)
                    }
                }


            }

            private fun joinGame(connId: String, connection: DataSnapshot) {
                connection
                    .child(playerUniqueId)
                    .child("player_name")
                    .ref.setValue(Firebase.auth.currentUser!!.email)

                for (player in connection.children) {

                    if (player.key != playerUniqueId) {
                        val getOpponentName = player.child("player_name").value as String
                        Toast.makeText(this@OnlineGameActivity, getOpponentName, Toast.LENGTH_SHORT).show()
                        opponentUniqueId = player.key.toString()

                        playerTurn = opponentUniqueId
                        // TODO: apply player turn

                        connectionId = connId
                        opponentFound = true

                        databaseReference.child("turns").child(connectionId)
                            .addValueEventListener(turnsEventListener)

                        databaseReference.child("connections").removeEventListener(this)
                        break
                    }

                }
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (opponentFound)
                    return

                if (snapshot.hasChildren()) {

                    for (connection in snapshot.children) {

                        val connId = connection.key!!
                        val getPlayersCount = connection.childrenCount.toInt()

                        if (createdGame && getPlayersCount == 2) {
                            this.addOpponent(connId, connection)
                            return
                        }
                        else if (!createdGame && getPlayersCount == 1) {
                            this.joinGame(connId, connection)
                            return
                        }

                    }

                    if (!createdGame) {
                        this.createGame(snapshot)
                    }

                }
                else if (!createdGame) {
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