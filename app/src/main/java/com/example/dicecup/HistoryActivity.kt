package com.example.dicecup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dicecup.model.HistoryEntity
import com.example.dicecup.service.HistoryService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val adapter = HistoryAdapter(this, HistoryService.getAll(), false)
        historyListView.adapter = adapter

        if(adapter.getCount() == 0) {
            Toast.makeText(
                this,
                "the history is empty",
                Toast.LENGTH_LONG
            ).show()
        }

        backButton.setOnClickListener {this.finish()}
        clearButton.setOnClickListener{
            HistoryService.clear()
            adapter.notifyDataSetChanged()
        }


        val togglePipImage = findViewById<ToggleButton>(R.id.changeToimageView)
        togglePipImage.setOnClickListener { changeViewType(togglePipImage.isChecked)}

        }

    private fun changeViewType(checked: Boolean) {
        if(checked){
            val adapter = HistoryAdapter(this, HistoryService.getAll(), true)
            val historyView = findViewById<ListView>(R.id.historyListView)
            historyView.adapter = adapter
        } else {
            val adapter = HistoryAdapter(this, HistoryService.getAll(), false)
            val lvRollHistory = findViewById<ListView>(R.id.historyListView)
            lvRollHistory.adapter = adapter
        }
    }

    //Adapter
    internal class HistoryAdapter(context: Context, private val history: MutableList<HistoryEntity>,private val asImage: Boolean)
        : ArrayAdapter<HistoryEntity>(context, 0, history)
    {
        // these colors are used to toggle the background of the list items.
        private val colours = intArrayOf(
            Color.parseColor("#AAAAAA"),
            Color.parseColor("#CACACA")
        )

        override fun getView(position: Int, v: View?, parent: ViewGroup): View {
            var v1: View? = v
            if (v1 == null) {
                val mInflater = LayoutInflater.from(context)
                v1 = mInflater.inflate(R.layout.cell_extended, null)

            }
            val resView: View = v1!!
            resView.setBackgroundColor(colours[position % colours.size])
            val h = history[position]
            val indexView = resView.findViewById<TextView>(R.id.indexItem)
            val timeView = resView.findViewById<TextView>(R.id.timeItem)
            val dicesView = resView.findViewById<TextView>(R.id.dicesItem)
            indexView.text = h.id.toString()
            timeView.text = h.timestamp.toString()
            //dicesView.text = rolls

            // List of dice images
            val imageViewList = ArrayList<ImageView>()
            imageViewList.add(resView.findViewById(R.id.Image1))
            imageViewList.add(resView.findViewById(R.id.Image2))
            imageViewList.add(resView.findViewById(R.id.Image3))
            imageViewList.add(resView.findViewById(R.id.Image4))
            imageViewList.add(resView.findViewById(R.id.Image5))
            imageViewList.add(resView.findViewById(R.id.Image6))



            if (asImage){
                dicesView.visibility = View.VISIBLE
                setDiceVisible(h.rolls.size, imageViewList)
                showDiceImages(100, h.rolls, imageViewList)
            }else{
                setDiceVisible(0,imageViewList)
                val temp = ArrayList<Int>()
                for (i in 0 until h.rolls.size step 1) {
                    temp.add(h.rolls[i])
                    dicesView.text = temp.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", " -")
                }

            }

            return resView
        }
        fun setDiceVisible(diceAmount: Int, imageViewList: ArrayList<ImageView>) {
            // Set all dice to gone
            for (i in 0 until imageViewList.size) {
                imageViewList[i].visibility = View.GONE
            }

            // Set needed amount of dice to visible
            for (i in 0 until diceAmount) {
                imageViewList[i].visibility = View.VISIBLE
            }
        }
        fun showDiceImages(imageSize: Int, diceRoll: IntArray, imageViewList: ArrayList<ImageView>) {
            for (i in 0 until diceRoll.size) {
                Picasso
                    .get()
                    .load(GetImageId(diceRoll[i]))
                    .resize(imageSize, imageSize)
                    .onlyScaleDown()
                    .into(imageViewList[i])
            }

        }
        fun GetImageId(eyes: Int): Int {
            if (eyes == 1) return R.drawable.dice1
            if (eyes == 2) return R.drawable.dice2
            if (eyes == 3) return R.drawable.dice3
            if (eyes == 4) return R.drawable.dice4
            if (eyes == 5) return R.drawable.dice5
            return R.drawable.dice6
        }
    }
}







