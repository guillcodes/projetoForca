package com.example.projetoforca.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoforca.ui.admin.TelaAdmin
import com.example.projetoforca.ui.admin.TelaAdminRanking
import com.example.projetoforca.ui.configuracoes.TelaConfiguracoes
import com.example.projetoforca.ui.telaInicial.TelaInicial
import com.example.projetoforca.ui.autenticacao.TelaLogin
import com.example.projetoforca.ui.classificacao.TelaClassificacao
import com.example.projetoforca.ui.jogo.TelaDeJogo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(auth: FirebaseAuth) {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "tela_inicial"
    ) {

        composable("tela_inicial") {
            val isLoggedIn = auth.currentUser != null

            TelaInicial(
                navController = navController,
                isLoggedIn = isLoggedIn,
                onLogout = {
                    auth.signOut()
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
                navController= navController,
                onLoginSuccess = {
                    navController.navigate("tela_inicial") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("admin") {
            TelaAdmin(navController = navController)
        }

        composable("classificacao") {
            TelaClassificacao(navController = navController)
        }

        composable("jogo") {
            TelaDeJogo(
                navController = navController,
                palavraAdmin = ""
            )
        }

        composable("jogar/{palavraChave}") { backStackEntry ->
            val palavra = backStackEntry.arguments?.getString("palavraChave") ?: ""
            val decoded = Uri.decode(palavra)

            TelaDeJogo(
                navController = navController,
                palavraAdmin = decoded
            )
        }

        composable("admin_ranking") {
            TelaAdminRanking(navController = navController)
        }
    }
}