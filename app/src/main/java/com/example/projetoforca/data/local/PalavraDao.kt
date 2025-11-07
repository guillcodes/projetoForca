// No arquivo: data/local/PalavraDao.kt
package com.example.projetoforca.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PalavraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(palavras: List<Palavra>)

    @Query("SELECT * FROM palavra_table WHERE categoria = :categoria ORDER BY RANDOM() LIMIT 1")
    suspend fun getPalavraAleatoria(categoria: String): Palavra?

    @Query("SELECT * FROM palavra_table")
    fun getAllPalavras(): Flow<List<Palavra>>

    @Query("DELETE FROM palavra_table")
    suspend fun clearAll()

    // --- FUNÇÃO NOVA ADICIONADA ---
    /**
     * Conta quantas palavras existem na tabela.
     */
    @Query("SELECT COUNT(*) FROM palavra_table")
    suspend fun count(): Int
}