package com.example.projetoforca.ui.admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class AdminViewModel : ViewModel() {

    private val _palavrasAleatorias = MutableStateFlow<List<String>>(emptyList())
    val palavrasAleatorias = _palavrasAleatorias.asStateFlow()

    private val todasPalavras = listOf(
        "Carro", "Janela", "Celular", "Futebol", "Cachorro",
        "Computador", "Cidade", "Escola", "Mesa", "Viagem",
        "Chocolate", "Internet", "Chuva", "Flor", "Montanha",
        "Praia", "Livro", "Gato", "Música", "Cinema"
    )

    var palavraChave: String = ""
        private set

    init {
        gerarPalavrasAleatorias()
    }

    fun gerarPalavrasAleatorias() {
        _palavrasAleatorias.value = todasPalavras.shuffled().take(10)
    }

    fun processarPalavrasSelecionadas(selecionadas: List<String>): Boolean {
        return if (selecionadas.size >= 5) {
            palavraChave = selecionadas.random()
            true
        } else {
            false
        }
    }
}
