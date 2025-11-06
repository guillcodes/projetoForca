// No arquivo: data/local/PalavraDao.kt
package com.example.projetoforca.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PalavraDao {

    /**
     * Insere uma lista de palavras (vindas do Firebase).
     * Se alguma palavra já existir, ela será substituída.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(palavras: List<Palavra>)

    /**
     * Pega uma palavra aleatória de uma categoria específica.
     * Esta será a função principal para iniciar o jogo.
     */
    @Query("SELECT * FROM palavra_table WHERE categoria = :categoria ORDER BY RANDOM() LIMIT 1")
    suspend fun getPalavraAleatoria(categoria: String): Palavra?

    /**
     * Pega todas as palavras (ex: para uma lista de admin local).
     */
    @Query("SELECT * FROM palavra_table")
    fun getAllPalavras(): Flow<List<Palavra>>

    /**
     * Limpa a tabela antiga antes de baixar uma nova lista do Firebase.
     */
    @Query("DELETE FROM palavra_table")
    suspend fun clearAll()
}