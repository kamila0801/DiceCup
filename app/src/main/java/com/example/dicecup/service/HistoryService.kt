package com.example.dicecup.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dicecup.model.HistoryEntity
import java.time.LocalDateTime

object HistoryService {

    private var historyList: MutableList<HistoryEntity> = ArrayList()

    fun getAll() = historyList

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(dices: String) {
        var values = dices.dropLast(3) //because last 3 chars are " - "
        historyList.add(HistoryEntity(historyList.size + 1, LocalDateTime.now(), values))
        for (h in historyList) println(h)
    }

    fun clear() {
        historyList.clear()
    }
}