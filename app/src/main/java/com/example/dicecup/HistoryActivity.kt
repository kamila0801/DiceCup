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
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val adapter = HistoryAdapter(this, HistoryService.getAll())
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
    }

    //Adapter
    internal class HistoryAdapter(context: Context, private val history: MutableList<HistoryEntity>)
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
            dicesView.text = h.dices

            return resView
        }
    }
}





