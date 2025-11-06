package com.example.projetoforca.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking_table")
data class Ranking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerName: String,
    val score: Int
)