package com.example.projetoforca.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.repository.RankingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AdminRankingUiState(
    val rankingList: List<Ranking> = emptyList()
)

class AdminRankingViewModel(private val repository: RankingRepository) : ViewModel() {

    val uiState: StateFlow<AdminRankingUiState> =
        repository.getRankingFlow()
            .map { AdminRankingUiState(rankingList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = AdminRankingUiState()
            )


    fun deleteRanking(ranking: Ranking) {
        viewModelScope.launch {
            repository.delete(ranking)
        }
    }


    fun addRanking(nome: String, score: Int) {
        if (nome.isBlank() || score < 0) return
        viewModelScope.launch {
            repository.insert(Ranking(playerName = nome, score = score))
        }
    }

    fun updateRanking(ranking: Ranking) {
        viewModelScope.launch {
            repository.update(ranking)
        }
    }
}

class AdminRankingViewModelFactory(private val repository: RankingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminRankingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminRankingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}