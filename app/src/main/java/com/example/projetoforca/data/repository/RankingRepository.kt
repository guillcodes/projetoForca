package com.example.projetoforca.data.repository

import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.local.RankingDao
import kotlinx.coroutines.flow.Flow

class RankingRepository(private val rankingDao: RankingDao) {

    fun getRankingFlow(): Flow<List<Ranking>> = rankingDao.getRankingFlow()

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