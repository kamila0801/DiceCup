package com.example.dicecup

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class HistoryActivity : AppCompatActivity() {
    private lateinit var historyView: ListView
    private lateinit var backBtn: Button
    private val mHistory = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyView = findViewById(R.id.dicesView)
        backBtn = findViewById(R.id.backButton)





        backToRollView()
        clearHistoryBtn()
        addHistoryToListView()

    }

    private fun backToRollView() {
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun clearHistoryBtn(){
        //need to do
    }


    private fun addHistoryToListView(){
        var toka = intent.getStringArrayListExtra("history")
        mHistory.add(toka.toString())

        val arrayAdapter: ArrayAdapter<*>

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  mHistory)
        historyView.adapter = arrayAdapter

    }








}





