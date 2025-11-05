package com.example.projetoforca.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoforca.ui.telaInicial.TelaInicial


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "tela_inicial") {

        composable("tela_inicial") {
            TelaInicial(navController = navController)
        }

        composable("classificacao") {
            Text("Tela de Classificação (placeholder)")
        }
        composable("jogo") {
            Text("Tela de Jogo (placeholder)")
        }
    }
}