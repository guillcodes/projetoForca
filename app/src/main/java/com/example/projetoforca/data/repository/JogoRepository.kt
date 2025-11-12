package com.example.projetoforca.data.repository

import com.example.projetoforca.data.local.Palavra
import com.example.projetoforca.data.local.PalavraDao


class JogoRepository(private val palavraDao: PalavraDao) {

    suspend fun getPalavraAleatoria(categoria: String): Palavra? {
        garantirBancoPopulado()

        return palavraDao.getPalavraAleatoria(categoria)
    }
    private suspend fun garantirBancoPopulado() {
        if (palavraDao.count() == 0) {
            popularBancoDeDados()
        }
    }

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