package com.tictactoe_master.online_game

import com.google.firebase.database.*

class FirebaseCommunication {
    private var gameId = ""
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val gameRef: DatabaseReference

    interface GameEventListener {
        fun onOpponentMove(row: Int, col: Int)
        fun onGameEnd(winner: String?)
    }

    constructor() {
        this.gameRef = database.getReference("games")
        this.gameId = gameRef.push().key ?: throw IllegalStateException("Failed to generate game ID.")
        gameRef.child(gameId).setValue(null)
    }

    constructor(gameId: String) {
        this.gameId = gameId
        this.gameRef = database.getReference("games/$gameId")
    }

    fun startListening(eventListener: GameEventListener) {
        gameRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Ignoruj to zdarzenie, ponieważ to inicjalizacja planszy
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val move = dataSnapshot.getValue(Move::class.java)
                move?.let {
                    eventListener.onOpponentMove(move.row, move.col)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // Plansza została zresetowana przez przeciwnika
                eventListener.onGameEnd(null)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Nie jest używane w tym przypadku
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Wystąpił błąd podczas nasłuchiwania zmian
            }
        })
    }

    fun makeMove(row: Int, col: Int) {
        val moveRef = gameRef.child("moves").push()
        val move = Move(row, col)
        moveRef.setValue(move)
    }

    fun resetGame() {
        gameRef.child("moves").removeValue()
    }

    fun endGame(winner: String?) {
        gameRef.removeValue()
    }

    data class Move(val row: Int = -1, val col: Int = -1)
}