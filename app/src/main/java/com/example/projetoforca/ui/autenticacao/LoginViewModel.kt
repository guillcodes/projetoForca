// No arquivo: LoginViewModel.kt
package com.example.projetoforca.ui.autenticacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    // Pega a instância do Firebase Authentication
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // _uiState é privado e mutável (só o ViewModel mexe)
    private val _uiState = MutableStateFlow(LoginUiState())
    // uiState é público e apenas para leitura (a UI vai "ouvir" ele)
    val uiState = _uiState.asStateFlow()

    // Função chamada pelo botão "Cadastrar"
    fun onRegisterClick(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email e Senha não podem estar vazios.") }
            return
        }

        viewModelScope.launch {
            // 1. Mostrar o "carregando"
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // 2. Tentar criar o usuário no Firebase
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 3a. Deu certo! Avisa a UI que o login foi um sucesso
                        _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                    } else {
                        // 3b. Deu erro. Mostra o erro na tela
                        val error = task.exception?.message ?: "Erro ao cadastrar"
                        _uiState.update { it.copy(isLoading = false, errorMessage = error) }
                    }
                }
        }
    }

    // Função chamada pelo botão "Entrar"
    fun onLoginClick(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email e Senha não podem estar vazios.") }
            return
        }

        viewModelScope.launch {
            // 1. Mostrar o "carregando"
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // 2. Tentar fazer o login no Firebase
            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 3a. Deu certo!
                        _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                    } else {
                        // 3b. Deu erro.
                        val error = task.exception?.message ?: "Email ou senha incorretos"
                        _uiState.update { it.copy(isLoading = false, errorMessage = error) }
                    }
                }
        }
    }

    // Função para "limpar" a mensagem de erro depois que ela for lida
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}