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

// 1. IMPORTAR A TELA DE LOGIN QUE CRIAMOS
import com.example.projetoforca.ui.autenticacao.TelaLogin


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "tela_inicial") {

        composable("tela_inicial") {
            TelaInicial(navController = navController)
        }

        // --- ROTAS ADICIONADAS ---

        composable("configuracoes") {
            TelaConfiguracoes(navController = navController)
        }

        // 2. ROTA DE LOGIN ATUALIZADA (NÃO É MAIS UM PLACEHOLDER)
        composable("login/cadastro") {
            // Chamamos a TelaLogin que você criou
            TelaLogin(
                onLoginSuccess = {
                    // Login deu certo!
                    // Navega para a tela "jogo" e limpa a pilha de navegação
                    // (para que o usuário não possa "voltar" para o login)
                    navController.navigate("jogo") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // --- ROTAS QUE JÁ EXISTIAM ---

        composable("classificacao") {
            Text("Tela de Classificação (placeholder)")
        }

        composable("jogo") {
            // Esta é a tela para onde o usuário vai após o login.
            // Quando você criá-la, substitua este placeholder.
            Text("Tela de Jogo (placeholder)")
        }

        // 3. ROTA PARA O ADMIN (vamos precisar dela em breve)
        composable("admin_dashboard") {
            Text("Tela de Admin (placeholder)")
        }
    }
}