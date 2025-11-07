// No arquivo: AppNavigation.kt
package com.example.projetoforca.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoforca.ui.configuracoes.TelaConfiguracoes
import com.example.projetoforca.ui.telaInicial.TelaInicial
import com.example.projetoforca.ui.autenticacao.TelaLogin
import com.example.projetoforca.ui.classificacao.TelaClassificacao
import com.example.projetoforca.ui.jogo.TelaDeJogo
import com.google.firebase.auth.FirebaseAuth // <-- IMPORTE O AUTH

@Composable
fun AppNavigation(auth: FirebaseAuth) { // <-- RECEBA O AUTH AQUI
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "tela_inicial") {

        composable("tela_inicial") {
            // LÓGICA DE LOGIN MOVIDA PARA CÁ
            val isLoggedIn = auth.currentUser != null

            TelaInicial(
                navController = navController,
                isLoggedIn = isLoggedIn, // Passa o status do login
                onLogout = {             // Passa a função de logout
                    auth.signOut()
                    // Volta para a tela inicial (agora deslogado)
                    // e limpa a pilha de navegação
                    navController.navigate("tela_inicial") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("configuracoes") {
            TelaConfiguracoes(navController = navController)
        }

        composable("login/cadastro") {
            TelaLogin(
                onLoginSuccess = {
                    // CORREÇÃO: Após o login, volte para a "tela_inicial".
                    // Ela vai recarregar e mostrar o botão "Sair".
                    navController.navigate("tela_inicial") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("classificacao") {
            TelaClassificacao(navController = navController)
        }

        composable("jogo") {
            TelaDeJogo(navController = navController)
        }

        composable("admin_dashboard") {
            Text("Tela de Admin (placeholder)")
        }
    }
}