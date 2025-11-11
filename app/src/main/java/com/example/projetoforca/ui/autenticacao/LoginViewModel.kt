
package com.example.projetoforca.ui.autenticacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onRegisterClick(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email e Senha não podem estar vazios.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            auth.createUserWithEmailAndPassword(email.trim(), pass.trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                    } else {
                        val error = task.exception?.message ?: "Erro ao cadastrar"
                        _uiState.update { it.copy(isLoading = false, errorMessage = error) }
                    }
                }
        }
    }

    fun onLoginClick(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email e Senha não podem estar vazios.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            auth.signInWithEmailAndPassword(email.trim(), pass.trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                    } else {
                        val error = task.exception?.message ?: "Email ou senha incorretos"
                        _uiState.update { it.copy(isLoading = false, errorMessage = error) }
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}