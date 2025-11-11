package com.example.projetoforca.ui.classificacao

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


data class ClassificacaoUiState(
    val rankingList: List<Ranking> = emptyList()
)

class ClassificacaoViewModel(private val repository: RankingRepository) : ViewModel() {

    val uiState: StateFlow<ClassificacaoUiState> =
        repository.getRankingFlow()
            .map { rankingList -> ClassificacaoUiState(rankingList = rankingList) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ClassificacaoUiState()
            )

    fun addRanking(ranking: Ranking) {
        viewModelScope.launch {
            repository.insert(ranking)
        }
    }

    fun deleteRanking(ranking: Ranking) {
        viewModelScope.launch {
            repository.delete(ranking)
        }
    }
}

class ClassificacaoViewModelFactory(private val repository: RankingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassificacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClassificacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}