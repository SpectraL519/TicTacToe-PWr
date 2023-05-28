package com.tictactoe_master.activity.ui

import android.annotation.SuppressLint
import android.app.ActionBar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tictactoe_master.GalleryFragment
import com.tictactoe_master.R
import com.tictactoe_master.activity.GalleryActivity
import com.tictactoe_master.app_data.CoinHandler
import com.tictactoe_master.app_data.FileDataHandler
import com.tictactoe_master.logic.utils.Figure

class GalleryAdapter(
    private val app: AppCompatActivity,
    private val fragment: GalleryFragment,
    private val sourceList: ArrayList<Array<Int>>
    ): RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cell_gallery, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = ActionBar.LayoutParams.WRAP_CONTENT
        layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT

        return GalleryViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.img1.setImageResource(sourceList[position][0])
        holder.img2.setImageResource(sourceList[position][1])
        val price = sourceList[position][2]
        if (sourceList[position][0] == Figure.O.getImageResource()){
            holder.selectCV.setCardBackgroundColor(app.getColor(R.color.dark_green))
        }
        if (price != 0){
            holder.selectTV.text = "$price \uD83E\uDE99"

            if(price > CoinHandler.getBalance()){
                holder.selectCV.setCardBackgroundColor(app.getColor(R.color.bg_color))
            }else{
                holder.selectCV.setCardBackgroundColor(app.getColor(R.color.light_green))
                holder.selectCV.setOnClickListener() {
                    val builder = AlertDialog.Builder(app)
                    builder.setMessage("Are you sure you want to buy this theme?")

                    builder.setPositiveButton("Yes") { _, _ ->
                        CoinHandler.setBalance(CoinHandler.getBalance()-price)
                        CoinHandler.saveBalance(app)
                        CoinHandler.loadBalance(app)
                        FileDataHandler.writeInt(app, "p${position}", 0)
                        selectTheme(holder,position)
                    }

                    builder.setNegativeButton("No") { _, _ ->}
                    builder.show()
                }
            }
        }else{
            holder.selectCV.setOnClickListener(){
                selectTheme(holder, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    private fun selectTheme(holder: GalleryViewHolder, position: Int){
        holder.selectTV.text = "select"
        FileDataHandler.writeInt(app, "img1", sourceList[position][0])
        FileDataHandler.writeInt(app, "img2", sourceList[position][1])
        Figure.O.setImageResource(sourceList[position][0])
        Figure.X.setImageResource(sourceList[position][1])
        sourceList[position][2] = 0

        holder.selectCV.setCardBackgroundColor(app.getColor(R.color.dark_green))
        fragment.setRecyclerView()
    }
}