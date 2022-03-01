package com.example.dicecup.model
import java.io.Serializable
import java.time.LocalDateTime

data class HistoryEntity(
    var id: Int,
    var timestamp: LocalDateTime,
    val rolls: IntArray) :
    Serializable {}