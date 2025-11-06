// No arquivo: data/local/AppDatabase.kt
package com.example.projetoforca.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ranking::class, Palavra::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Expõe o DAO do Ranking
    abstract fun rankingDao(): RankingDao

    // (LINHA NOVA) Expõe o DAO da Palavra
    abstract fun palavraDao(): PalavraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}