// No arquivo: TelaLogin.kt
package com.example.projetoforca.ui.autenticacao

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaLogin(
    // Esta função será chamada quando o login der certo, para navegar para outra tela
    onLoginSuccess: () -> Unit,
    // Pega uma instância do nosso ViewModel
    viewModel: LoginViewModel = viewModel()
) {
    // Pega o estado da UI (UiState) vindo do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Variáveis para guardar o que o usuário digita
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // "Ouve" o estado de erro
    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            viewModel.clearError() // Limpa o erro depois de mostrar
        }
    }

    // "Ouve" o estado de sucesso no login
    LaunchedEffect(key1 = uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
            onLoginSuccess() // Chama a função para navegar para a próxima tela
        }
    }

    // --- Começa a UI ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Jogo da Forca", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Se estiver carregando, mostra o ícone de loading
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.onLoginClick(email, password) }) {
                    Text("Entrar")
                }
                Button(onClick = { viewModel.onRegisterClick(email, password) }) {
                    Text("Cadastrar")
                }
            }
        }
    }
}