package com.tictactoe_master.activity

import android.app.ProgressDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R
import com.tictactoe_master.logic.CoinHandler
import com.tictactoe_master.logic.utils.Coordinates
import com.tictactoe_master.logic.utils.Figure
import com.tictactoe_master.logic.win_condition.IWinCondition

class OnlineGameActivity : GameActivity() {
    private val playerName = Firebase.auth.currentUser!!.email!!
    private var databaseReference = FirebaseDatabase
        .getInstance()
        .getReferenceFromUrl("https://tictactoe-master-9efa8-default-rtdb.firebaseio.com/")
    private var playerUniqueId = "0"
    private var opponentUniqueId = "0"
    private var opponentName = ""
    private var connectionId = ""
    private var player = Figure.EMPTY
    private lateinit var movesEventListener: ValueEventListener
    private lateinit var opponentNameTV: TextView
    private lateinit var playingAsTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initConnection()
    }

    override fun initView() {
        super.initView()
        this.opponentNameTV = findViewById(R.id.opponent_name_tv)
        this.playingAsTV = findViewById(R.id.playing_as_tv)
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

    override fun cellClick(imageView: ImageView, x: Int, y: Int) {
        if (this.game.state.currentPlayer == this.player) {
            if (this.game.placeFigure(x, y)) {
                val figure = this.game.state.getFigure(x, y)
                this.cells[x][y].setImageResource(figure.getImageResource())
                checkDimensions()
                this.turnTV.text = String.format("TURN: %s", figure.next().toString())
                val status = this.game.checkStatus()
                if (status.result != IWinCondition.Result.NONE) {
                    if (status.result == IWinCondition.Result.O || status.result == IWinCondition.Result.X) {
                        for (c in status.coordinates) {
                            this.cells[c.row][c.column].setBackgroundColor(getColor(R.color.light_green))
                        }
                        this.nextBT.text = this.game.nextPointActionString
                    }
                    this.updateScoreView()
                }
                this.databaseReference
                    .child("moves")
                    .child(this.connectionId)
                    .ref.setValue(Coordinates(x, y).toString())
            }
        } else
            Toast.makeText(this, "Waiting for opponent's move", Toast.LENGTH_SHORT).show()
    }

    override fun gameOver(
        result: IWinCondition.Result,
        _winCondition: IWinCondition,
        _points: Int
    ) {
        if (result == IWinCondition.Result.TIE) {
            CoinHandler.gameOver(1, _winCondition, size, _points)
        } else if (result.toString() == player.toString()) {
            CoinHandler.gameOver(2, _winCondition, size, _points)
        }
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
                this@OnlineGameActivity.connectionId = System.currentTimeMillis().toString()
                snapshot.child(this@OnlineGameActivity.connectionId)
                    .child("params")
                    .ref.setValue(this@OnlineGameActivity.encodeGameParams())
                snapshot.child(this@OnlineGameActivity.connectionId)
                    .child(this@OnlineGameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(this@OnlineGameActivity.playerName)
            }

            private fun addOpponent(connId: String, connection: DataSnapshot) {
                if (this@OnlineGameActivity.connectionId != connId){
                    return
                }
                if (connection.child("params").value.toString() != this@OnlineGameActivity.encodeGameParams())
                    return
                this@OnlineGameActivity.player = Figure.O
                this@OnlineGameActivity.playingAsTV.text = "Playing as: O"
                for (player in connection.children) {
                    if (player.key != "params" && player.key != this@OnlineGameActivity.playerUniqueId) {
                        this.opponentFound = true
                        this@OnlineGameActivity.opponentName = player.child("player_name").value as String
                        this@OnlineGameActivity.opponentUniqueId = player.key.toString()
                        this@OnlineGameActivity.connectionId = connId
                        this@OnlineGameActivity.databaseReference
                            .child("moves")
                            .child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.movesEventListener)
                        progressDialog.hide()
                        this@OnlineGameActivity.opponentNameTV.text = String.format(
                            "Playing with %s",
                            this@OnlineGameActivity.opponentName
                        )
                        this@OnlineGameActivity.databaseReference
                            .child("connections")
                            .removeEventListener(this)
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
                        this@OnlineGameActivity.player = Figure.X
                        this@OnlineGameActivity.playingAsTV.text = "Playing as: X"
                        this@OnlineGameActivity.connectionId = connId
                        this@OnlineGameActivity.databaseReference
                            .child("moves")
                            .child(this@OnlineGameActivity.connectionId)
                            .addValueEventListener(this@OnlineGameActivity.movesEventListener)
                        progressDialog.hide()
                        this@OnlineGameActivity.opponentNameTV.text = String.format(
                            "Playing with %s",
                            this@OnlineGameActivity.opponentName
                        )
                        this@OnlineGameActivity.databaseReference
                            .child("connections")
                            .removeEventListener(this)
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
                        } else if (!this.createdGame && getPlayersCount == 1) {
                            this.joinGame(connId, connection)
                        }
                    }
                    if (!this.opponentFound && !this.createdGame) {
                        this.createGame(snapshot)
                    }
                } else if (!this.createdGame) {
                    this.createGame(snapshot)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        this.movesEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val coordinates = Coordinates.fromString(snapshot.value.toString())
                if (coordinates != null) {
                    val x = coordinates.row
                    val y = coordinates.column
                    this@OnlineGameActivity.game.placeFigure(x, y)
                    val figure = this@OnlineGameActivity.game.state.getFigure(x, y)
                    this@OnlineGameActivity.cells[x][y].setImageResource(figure.getImageResource())
                    checkDimensions()
                    this@OnlineGameActivity.turnTV.text = String.format("TURN: %s", figure.next().toString())
                    val status = this@OnlineGameActivity.game.checkStatus()
                    if (status.result != IWinCondition.Result.NONE) {
                        if (status.result == IWinCondition.Result.O || status.result == IWinCondition.Result.X) {
                            for (c in status.coordinates) {
                                this@OnlineGameActivity.cells[c.row][c.column]
                                    .setBackgroundColor(getColor(R.color.light_green))
                            }
                            this@OnlineGameActivity.nextBT.text = this@OnlineGameActivity.game.nextPointActionString
                        }
                        this@OnlineGameActivity.updateScoreView()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
    }
}
