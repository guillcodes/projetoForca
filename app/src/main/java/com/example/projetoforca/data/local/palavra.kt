package com.example.projetoforca.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palavra_table")
data class Palavra(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val palavra: String,
    val categoria: String
)