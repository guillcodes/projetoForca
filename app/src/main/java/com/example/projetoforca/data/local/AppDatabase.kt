// No arquivo: data/local/AppDatabase.kt
package com.example.projetoforca.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ranking::class, Palavra::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rankingDao(): RankingDao
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
                )
                    // O Callback foi removido daqui
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}