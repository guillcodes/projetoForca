package com.example.projetoforca.data.repository

import com.example.projetoforca.data.local.Palavra
import com.example.projetoforca.data.local.PalavraDao

/**
 * Repositório para gerenciar as palavras.
 * Agora ele também é responsável por popular o banco na primeira execução.
 */
class JogoRepository(private val palavraDao: PalavraDao) {

    /**
     * Busca uma palavra aleatória de uma categoria específica.
     */
    suspend fun getPalavraAleatoria(categoria: String): Palavra? {
        // 1. CHAMA A FUNÇÃO DE VERIFICAÇÃO PRIMEIRO
        garantirBancoPopulado()

        // 2. AGORA, BUSCA A PALAVRA (o banco não estará mais vazio)
        return palavraDao.getPalavraAleatoria(categoria)
    }

    /**
     * Verifica se o banco está vazio e, se estiver, o popula.
     */
    private suspend fun garantirBancoPopulado() {
        // Verifica se o banco está vazio usando a nova função count()
        if (palavraDao.count() == 0) {
            popularBancoDeDados()
        }
    }

    /**
     * Adiciona palavras de exemplo ao banco de dados.
     */
    private suspend fun popularBancoDeDados() {
        val listaDePalavras = listOf(
            Palavra(palavra = "BANANA", categoria = "Frutas"),
            Palavra(palavra = "MACA", categoria = "Frutas"),
            Palavra(palavra = "UVA", categoria = "Frutas"),
            Palavra(palavra = "ABACAXI", categoria = "Frutas"),
            Palavra(palavra = "MELANCIA", categoria = "Frutas"),
            Palavra(palavra = "CACHORRO", categoria = "Animais"),
            Palavra(palavra = "GATO", categoria = "Animais"),
            Palavra(palavra = "LEAO", categoria = "Animais"),
            Palavra(palavra = "CARRO", categoria = "Objetos"),
            Palavra(palavra = "CADEIRA", categoria = "Objetos")
        )
        palavraDao.insertAll(listaDePalavras)
    }
}