package com.tictactoe_master.activity.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_master.R
import com.tictactoe_master.activity.ChooseGameTypeActivity
import com.tictactoe_master.logic.CoinHandler
import com.tictactoe_master.app_data.FileDataHandler
import com.tictactoe_master.logic.utils.Figure

class MainFragment : Fragment() {

    private lateinit var oneVsOneCV: CardView
    private lateinit var oneVsBotCV: CardView
    private lateinit var oneVsOneOnlineCV: CardView
    private lateinit var shopCV: CardView

    private lateinit var myView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView =inflater.inflate(R.layout.fragment_main, container, false)



        this.initPrices()
        this.initView()

        Figure.O.setImageResource(FileDataHandler.readInt(activity as AppCompatActivity, "img1"))
        Figure.X.setImageResource(FileDataHandler.readInt(activity as AppCompatActivity, "img2"))
        // Inflate the layout for this fragment
        return myView
    }

    override fun onStop() {
        super.onStop()
        CoinHandler.saveBalance(activity as AppCompatActivity)
    }

    private fun initView() {
        this.oneVsOneCV = myView.findViewById(R.id.one_v_one_cv)
        this.oneVsBotCV = myView.findViewById(R.id.one_v_bot_cv)
        this.oneVsOneOnlineCV = myView.findViewById(R.id.one_v_one_online_cv)
        this.shopCV = myView.findViewById(R.id.shop_cv)


        this.oneVsOneCV.setOnClickListener {
            this.startGame("1_v_1")
        }

        this.oneVsBotCV.setOnClickListener {
            this.startGame("1_v_bot")
        }

        this.oneVsOneOnlineCV.setOnClickListener {
            if (Firebase.auth.currentUser == null)
                Toast.makeText(activity, "First log in to play online", Toast.LENGTH_SHORT).show()
            else
                this.startGame("1_v_1_online")
        }

        this.shopCV.setOnClickListener {
            val tabs = requireActivity().findViewById<TabLayout>(R.id.tabs)
            tabs.getTabAt(1)?.select()
        }
    }

    private fun startGame (gameMode: String) {
        val gameIntent = Intent(activity, ChooseGameTypeActivity::class.java).apply {
            putExtra("game_mode", gameMode)
        }
        startActivity(gameIntent)
    }

    private fun initPrices(){
        if(!FileDataHandler.checkInt(activity as AppCompatActivity, "p0")){
            FileDataHandler.writeInt(activity as AppCompatActivity, "img1", R.drawable.circle)
            FileDataHandler.writeInt(activity as AppCompatActivity, "img2", R.drawable.cross)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p0", 0)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p1", 0)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p2", 20)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p3", 20)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p4", 20)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p5", 50)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p6", 100)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p7", 200)
            FileDataHandler.writeInt(activity as AppCompatActivity, "p8", 200)
        }
    }

}