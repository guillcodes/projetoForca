package com.example.projetoforca.ui.jogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projetoforca.data.local.Palavra
import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.repository.JogoRepository
import com.example.projetoforca.data.repository.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
enum class GameState { JOGANDO, VENCEU, PERDEU, CARREGANDO, ERRO_PALAVRA }

data class JogoUiState(
    val palavraSecreta: String = "",
    val palavraExibida: String = "",
    val categoria: String = "",
    val letrasUsadas: Set<Char> = emptySet(),
    val erros: Int = 0,
    val maxErros: Int = 6,
    val estadoDoJogo: GameState = GameState.CARREGANDO
)

class JogoViewModel(
    private val jogoRepository: JogoRepository,
    private val rankingRepository: RankingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JogoUiState())
    val uiState: StateFlow<JogoUiState> = _uiState.asStateFlow()

    fun iniciarJogo(palavraForcada: String, categoria: String) {
        _uiState.value = JogoUiState(estadoDoJogo = GameState.CARREGANDO)
        viewModelScope.launch {

            val palavraObj: Palavra?

            if (palavraForcada.isNotBlank()) {

                palavraObj = Palavra(
                    palavra = palavraForcada.uppercase().trim(),
                    categoria = "Admin" // Define a dica como "Admin"
                )
            } else {
                palavraObj = jogoRepository.getPalavraAleatoria(categoria)
            }

            if (palavraObj == null) {
                _uiState.update { it.copy(estadoDoJogo = GameState.ERRO_PALAVRA, categoria = "Erro") }
                return@launch
            }

            val palavra = palavraObj.palavra.uppercase().trim()
            _uiState.value = JogoUiState(
                palavraSecreta = palavra,
                palavraExibida = construirPalavraExibida(palavra, emptySet()),
                categoria = palavraObj.categoria,
                letrasUsadas = emptySet(),
                erros = 0,
                estadoDoJogo = GameState.JOGANDO
            )
        }
    }

    fun tentarLetra(letra: Char) {
        val currentState = _uiState.value
        if (currentState.letrasUsadas.contains(letra) || currentState.estadoDoJogo != GameState.JOGANDO) {
            return
        }
        val novasLetrasUsadas = currentState.letrasUsadas + letra
        val acertou = currentState.palavraSecreta.contains(letra)
        if (acertou) {
            val novaPalavraExibida = construirPalavraExibida(currentState.palavraSecreta, novasLetrasUsadas)
            val venceu = !novaPalavraExibida.contains('_')
            _uiState.update {
                it.copy(
                    palavraExibida = novaPalavraExibida,
                    letrasUsadas = novasLetrasUsadas,
                    estadoDoJogo = if (venceu) GameState.VENCEU else GameState.JOGANDO
                )
            }
        } else {
            val novosErros = currentState.erros + 1
            val perdeu = novosErros >= currentState.maxErros
            _uiState.update {
                it.copy(
                    letrasUsadas = novasLetrasUsadas,
                    erros = novosErros,
                    estadoDoJogo = if (perdeu) GameState.PERDEU else GameState.JOGANDO
                )
            }
        }
    }

    private fun construirPalavraExibida(palavra: String, letrasUsadas: Set<Char>): String {
        return palavra.map { char ->
            if (char == ' ' || letrasUsadas.contains(char)) char else '_'
        }.joinToString(" ")
    }

    fun salvarPontuacao(nomeJogador: String) {
        if (_uiState.value.estadoDoJogo != GameState.VENCEU) return
        val pontuacao = 100 - (_uiState.value.erros * 10)

        viewModelScope.launch {
            rankingRepository.insert(
                Ranking(playerName = nomeJogador, score = pontuacao) //
            )
        }
    }
}

class JogoViewModelFactory(
    private val jogoRepository: JogoRepository,
    private val rankingRepository: RankingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JogoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JogoViewModel(jogoRepository, rankingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}