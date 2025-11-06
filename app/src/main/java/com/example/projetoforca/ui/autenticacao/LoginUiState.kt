// No arquivo: LoginUiState.kt
package com.example.projetoforca.ui.autenticacao

data class LoginUiState(
    val isLoading: Boolean = false,      // Mostra um ícone de "carregando"
    val loginSuccess: Boolean = false, // Vira 'true' se o login/cadastro der certo
    val errorMessage: String? = null   // Guarda mensagens de erro (ex: "senha errada")
)