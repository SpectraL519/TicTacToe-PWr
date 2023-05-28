package com.tictactoe_master.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R
import com.tictactoe_master.app_data.CoinHandler
import com.tictactoe_master.logic.game.ClassicGame
import com.tictactoe_master.logic.game.IGame
import com.tictactoe_master.logic.game.PointGame
import com.tictactoe_master.logic.win_condition.ClassicWinCondition
import com.tictactoe_master.logic.win_condition.IWinCondition
import com.tictactoe_master.logic.win_condition.MobiusStripWinCondition


open class GameActivity : AppCompatActivity() {
    protected var size = 3
    private lateinit var accountTV: TextView
    private lateinit var pointsO: TextView
    private lateinit var pointsTie: TextView
    private lateinit var pointsX: TextView
    private lateinit var gameBoardTL: TableLayout
    private lateinit var coinsTV: TextView
    protected lateinit var game: IGame
    protected lateinit var turnTV: TextView
    protected lateinit var cells: Array<Array<ImageView>>
    protected lateinit var nextBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        this.initLogic()
        this.initView()
    }

    override fun onStart() {
        super.onStart()
        this.setAccountTVText()
    }

    override fun onStop() {
        super.onStop()
        CoinHandler.saveBalance(this)
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    protected open fun initLogic() {
        this.size = intent.getIntExtra("size", 3)
        val winCondition = when (intent.getStringExtra("win_cond")) {
            "mobius" -> MobiusStripWinCondition
            else -> ClassicWinCondition
        }
        this.game = when (intent.getStringExtra("game_type")) {
            "point" -> PointGame(
                this,
                this.size,
                winCondition,
                intent.getIntExtra("points_to_win", 2)
            )
            else -> ClassicGame(this, this.size, winCondition)
        }
    }

    protected open fun initView() {
        this.pointsO = findViewById(R.id.points_o_tv)
        this.pointsTie = findViewById(R.id.points_tie_tv)
        this.pointsX = findViewById(R.id.points_x_tv)
        this.accountTV = findViewById(R.id.account_tv)
        this.coinsTV = findViewById(R.id.coins_tv)
        this.updateScoreView()
        this.coinsTV.text = String.format(
            "%s %s",
            CoinHandler.getBalance(),
            getText(R.string.currency)
        )
        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            } else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }
        this.turnTV = findViewById(R.id.turn_tv)
        this.turnTV.text = String.format("TURN: %s", this.game.state.currentPlayer.toString())
        this.gameBoardTL = findViewById(R.id.game_board_tl)
        this.gameBoardTL.removeAllViews()
        this.cells = Array(this.size) { Array(this.size) { ImageView(this) } }
        for (i in 0 until this.size) {
            val tableRow = TableRow(this)
                for (j in 0 until this.size) {
                    val layoutParams = TableRow.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1f
                    )
                    layoutParams.setMargins(3, 3, 3, 3)
                    this.cells[i][j].layoutParams = layoutParams
                    this.cells[i][j].setBackgroundColor(Color.LTGRAY)
                    this.cells[i][j].setImageResource(android.R.color.transparent)
                    this.cells[i][j].scaleType = ImageView.ScaleType.FIT_XY
                    this.cells[i][j].setOnClickListener { cellClick(cells[i][j], i, j) }
                    tableRow.addView(this.cells[i][j])
            }
            val rowParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            tableRow.layoutParams = rowParams
            this.gameBoardTL.addView(tableRow)
            this.gameBoardTL.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    gameBoardTL.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    gameBoardTL.height //height is ready
                }
            })
            this.nextBT = findViewById(R.id.next_bt)
            this.nextBT.text = this.game.nextPointActionString
            this.nextBT.setOnClickListener {
                if (this.game.state.gameBlocked) {
                    // clear win mark
                    for (x in 0 until this.size) {
                        for (y in 0 until this.size)
                            this.cells[x][y].setBackgroundColor(Color.LTGRAY)
                    }
                    // clear figures
                    val coordinates = this.game.nextPointAction()
                    if (coordinates == null) {
                        for (x in 0 until this.size) {
                            for (y in 0 until this.size)
                                this.cells[x][y].setImageResource(android.R.color.transparent)
                        }
                    }
                    else {
                        for (c in coordinates)
                            this.cells[c.row][c.column].setImageResource(android.R.color.transparent)
                    }
                    this.turnTV.text = String.format(
                        "TURN: %s",
                        this.game.state.currentPlayer.toString()
                    )
                    this.nextBT.text = this.game.nextPointActionString
                    this.updateScoreView()
                }
            }
        }
    }

    protected open fun cellClick(imageView: ImageView, x: Int, y: Int) {
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
        }
    }

    protected fun checkDimensions(){
        if (this.cells[0][0].layoutParams.height == -1 && this.cells[0][0].width != 0){
            val w = this.cells[0][0].width
            for (i in 0 until this.size) {
                for (j in 0 until this.size) {
                    this.cells[i][j].layoutParams.height = w
                }
            }
        }
    }

    protected fun updateScoreView() {
        this.pointsO.text = String.format(
            "%s %s",
            getString(R.string.player_o),
            this.game.state.score[IWinCondition.Result.O].toString()
        )
        this.pointsTie.text = String.format(
            "%s %s",
            getString(R.string.tie),
            this.game.state.score[IWinCondition.Result.TIE].toString()
        )
        this.pointsX.text = String.format(
            "%s %s",
            getString(R.string.player_x),
            this.game.state.score[IWinCondition.Result.X].toString()
        )
    }

    fun showWinMessage(result: IWinCondition.Result) {
        val message =
            if (result == IWinCondition.Result.TIE)
                result.toString()
            else
                "player $result won!"
        this.coinsTV.text = String.format(
            "%s %s",
            CoinHandler.getBalance(),
            getText(R.string.currency)
        )
        Toast.makeText(
            this,
            "Game Over: $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}
