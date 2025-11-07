package com.example.projetoforca.data.repository

import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.local.RankingDao
import kotlinx.coroutines.flow.Flow

/**
 * Repositório que gerencia os dados do Ranking.
 * Ele depende do RankingDao para acessar o banco de dados Room.
 */
class RankingRepository(private val rankingDao: RankingDao) {

    /**
     * Expõe o Flow reativo do DAO para o ViewModel.
     * Isso cumpre o requisito de reatividade com Flow.
     */
    fun getRankingFlow(): Flow<List<Ranking>> = rankingDao.getRankingFlow()

    /**
     * As funções 'suspend' expõem as operações de CRUD (Create, Update, Delete)
     * para o ViewModel, conforme exigido pelo PDF.
     */
    suspend fun insert(ranking: Ranking) {
        rankingDao.insert(ranking)
    }

    suspend fun update(ranking: Ranking) {
        rankingDao.update(ranking)
    }

    suspend fun delete(ranking: Ranking) {
        rankingDao.delete(ranking)
    }
}