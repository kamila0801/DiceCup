package com.example.dicecup.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dicecup.model.HistoryEntity
import java.time.LocalDateTime

object HistoryService {

    private var historyList: MutableList<HistoryEntity> = ArrayList()

    fun getAll() = historyList

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(a : IntArray) {
        historyList.add(HistoryEntity(historyList.size + 1, LocalDateTime.now(), a))
    }

    fun clear() {
        historyList.clear()
    }
}