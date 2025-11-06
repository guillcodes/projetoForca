// No arquivo: data/local/RankingDao.kt
package com.example.projetoforca.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {

    /**
     * Create (Criar)
     * Insere uma nova pontuação. Se já existir, substitui.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ranking: Ranking)

    /**
     * Read (Ler)
     * Pega todos os scores da tabela, ordenados do maior para o menor.
     * O PDF exige o uso de Flow<List<...>> [cite: 35]
     */
    @Query("SELECT * FROM ranking_table ORDER BY score DESC")
    fun getRankingFlow(): Flow<List<Ranking>>

    /**
     * Update (Atualizar)
     * Atualiza uma entrada de ranking existente.
     */
    @Update
    suspend fun update(ranking: Ranking)

    /**
     * Delete (Apagar)
     * Apaga uma entrada de ranking.
     */
    @Delete
    suspend fun delete(ranking: Ranking)
}