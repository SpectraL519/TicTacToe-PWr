package com.tictactoe_master.online_game

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class FirebaseCommunication {

    private var gameId = ""
    private val playerId = Firebase.auth.currentUser!!.email!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun findOpponent() {
        var opponentFound = false
        var waiting = false

        var opponentId = "0"
        var playerTurn = ""

        database.reference.child("games").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (opponentFound) {

                    if (snapshot.hasChildren()) {

                        for (game in snapshot.children) {
                            val foundGameId = game.key!!.toLong()
                            val playerCount = game.childrenCount.toInt()

                            if (waiting) {
                                if (playerCount == 2) {

                                    playerTurn = playerId

                                    var playerFound = false
                                    for (player in game.children) {
                                        val foundPlayerId = player.key
                                        if (foundPlayerId == playerId) {
                                            playerFound = true
                                        }
                                        else if (playerFound) {
                                            opponentId = player.key!!
                                            gameId = foundGameId.toString()
                                            opponentFound = true
                                            database.getReference("games").child("turns").child
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {

                    gameId = System.currentTimeMillis().toString()
                    snapshot.child(gameId).child("player_id").ref.setValue(playerId)
                    waiting = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}