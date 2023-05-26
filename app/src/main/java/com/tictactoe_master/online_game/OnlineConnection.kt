package com.tictactoe_master.online_game

import com.google.firebase.database.*
import java.util.EventListener

class OnlineConnection {

    private var databaseReference = FirebaseDatabase
        .getInstance()
        .getReferenceFromUrl("https://console.firebase.google.com/u/0/project/tictactoe-master-9efa8/database/tictactoe-master-9efa8-default-rtdb/data/~2F")

    private var playerUniqueId = "0"
    private var opponentFound = false
    private var opponentUniqueId = "0"
    private var status = "matching" // matching or waiting
    private var playerTurn = ""
    private var connectionId = ""

    private lateinit var turnsEventListener: ValueEventListener
    private lateinit var wonEventListener: ValueEventListener

    fun createOrJoinGame() {
        // TODO: dodać jakiś ProcessDialog "waiting for opponent -> not cancelable

        this.playerUniqueId = System.currentTimeMillis().toString()

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

        this.wonEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        this.databaseReference.child("connections").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (opponentFound) {

                    if (snapshot.hasChildren()) {

                        for (connection in snapshot.children) {

                            val connId = connection.key!!
                            val getPlayersCount = connection.childrenCount.toInt()

                            if (status == "waiting") {

                                if (getPlayersCount == 2) {

                                    playerTurn = playerUniqueId
                                    // TODO: apply player turn

                                    var playerFound = false
                                    for (player in connection.children) {

                                        if (player.key == playerUniqueId) {
                                            playerFound = true
                                        }
                                        else {
                                            var getOpponentPlayerName = player.child("player_name").value as String
                                            opponentUniqueId = player.key.toString()
                                            connectionId = connId
                                            opponentFound = true

                                            databaseReference.child("turns").child(connectionId)
                                                .addValueEventListener(turnsEventListener)
                                            databaseReference.child("won").child(connectionId)
                                                .addValueEventListener(wonEventListener)

                                            // TODO: hide ProcessDialog

                                            databaseReference.child("connections").removeEventListener(this)
                                        }

                                    }

                                }

                            }
                            else {

                                if (getPlayersCount == 1) {

                                    connection
                                        .child(playerUniqueId)
                                        .child("player_name")
                                        .ref.setValue("some_player_name(change)")

                                    for (player in connection.children) {

                                        var getOpponentName = player.child("player_name").value as String
                                        opponentUniqueId = player.key.toString()

                                        playerTurn = opponentUniqueId
                                        // TODO: apply player turn

                                        connectionId = connId
                                        opponentFound = true

                                        databaseReference.child("turns").child(connectionId)
                                            .addValueEventListener(turnsEventListener)
                                        databaseReference.child("won").child(connectionId)
                                            .addValueEventListener(wonEventListener)

                                        databaseReference.child("connections").removeEventListener(this)

                                        break
                                    }

                                }

                            }

                        }

                        if (!opponentFound && status != "waiting") {

                            val connectionUniqueId = System.currentTimeMillis().toString()
                            snapshot.child(connectionUniqueId)
                                .child(playerUniqueId)
                                .child("player_name")
                                .ref.setValue("some_player_name(change)")

                            status = "waiting"

                        }

                    }
                    else {

                        val connectionUniqueId = System.currentTimeMillis().toString()
                        snapshot.child(connectionUniqueId)
                            .child(playerUniqueId)
                            .child("player_name")
                            .ref.setValue("some_player_name(change)")

                        status = "waiting"
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}