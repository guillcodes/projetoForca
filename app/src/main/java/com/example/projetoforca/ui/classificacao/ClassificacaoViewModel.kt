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

/**
 * O UiState obrigatório para este ViewModel.
 * Ele armazena a lista de ranking que a UI irá observar.
 */
data class ClassificacaoUiState(
    val rankingList: List<Ranking> = emptyList()
)

/**
 * O ViewModel da Tela de Classificação.
 * Ele recebe o RankingRepository via injeção.
 */
class ClassificacaoViewModel(private val repository: RankingRepository) : ViewModel() {

    /**
     * Coleta o Flow do repositório e o transforma em um StateFlow
     * para a UI consumir de forma reativa.
     */
    val uiState: StateFlow<ClassificacaoUiState> =
        repository.getRankingFlow() // Pega o Flow<List<Ranking>>
            .map { rankingList -> ClassificacaoUiState(rankingList = rankingList) } // Converte para UiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ClassificacaoUiState() // Estado inicial
            )

    // Funções para o CRUD (C-Create, U-Update, D-Delete)
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

/**
 * Esta é a ViewModel Factory, OBRIGATÓRIA pelo PDF.
 * Ela permite "injetar" o RankingRepository no ClassificacaoViewModel.
 */
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