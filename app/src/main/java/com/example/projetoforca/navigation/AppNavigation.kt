package com.example.projetoforca.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoforca.ui.configuracoes.TelaConfiguracoes
import com.example.projetoforca.ui.telaInicial.TelaInicial


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "tela_inicial") {

        composable("tela_inicial") {
            TelaInicial(navController = navController)
        }

        // --- ROTAS ADICIONADAS ---

        // 2. ROTA PARA A TELA DE CONFIGURAÇÕES (chamada pelo ícone)
        composable("configuracoes") {
            TelaConfiguracoes(navController = navController)
        }

        // 3. ROTA PARA LOGIN/CADASTRO (chamada pelo botão "JOGAR")
        composable("login/cadastro") {
            // Você não forneceu esta tela, então usei um placeholder.
            // Quando criar a TelaLogin, substitua este Text() por ela.
            Text("Tela de Login/Cadastro (placeholder)")
        }

        // --- ROTAS QUE JÁ EXISTIAM ---

        composable("classificacao") {
            Text("Tela de Classificação (placeholder)")
        }

        composable("jogo") {
            Text("Tela de Jogo (placeholder)")
        }
    }
}