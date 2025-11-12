package com.example.projetoforca.ui.admin

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun TelaAdmin(
    navController: NavHostController,
    viewModel: AdminViewModel = viewModel()
) {
    val context = LocalContext.current
    val palavras = viewModel.palavrasAleatorias.collectAsState().value
    val selecoes = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(palavras) {
        palavras.forEach { p ->
            selecoes.putIfAbsent(p, false)
        }
        val chavesAtuais = palavras.toSet()
        val paraRemover = selecoes.keys.filter { it !in chavesAtuais }
        paraRemover.forEach { selecoes.remove(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1C))
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Painel Admin",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    navController.navigate("admin_ranking")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF5350),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gerenciar Ranking (CRUD)")
            }

            Divider(modifier = Modifier.padding(vertical = 24.dp), color = Color.Gray)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                palavras.forEach { palavra ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = selecoes[palavra] ?: false,
                                onValueChange = { selecoes[palavra] = it }
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selecoes[palavra] ?: false,
                            onCheckedChange = { selecoes[palavra] = it }
                        )
                        Text(text = palavra, color = Color.White, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            val selecionadasList by remember {
                derivedStateOf { selecoes.filter { it.value }.keys.toList() }
            }

            Button(
                onClick = {
                    val selecionadas = selecoes.filter { it.value }.keys.toList()
                    val sucesso = viewModel.processarPalavrasSelecionadas(selecionadas)
                    if (!sucesso) {
                        Toast.makeText(context, "Selecione no mínimo 5 palavras!", Toast.LENGTH_SHORT).show()
                    } else {
                        val chave = viewModel.palavraChave
                        Toast.makeText(context, "Palavra-chave definida: $chave", Toast.LENGTH_LONG).show()
                        val encoded = Uri.encode(chave)
                        navController.navigate("jogar/$encoded") {
                            popUpTo(0)
                        }
                        selecoes.keys.forEach { selecoes[it] = false }
                    }
                },
                enabled = selecionadasList.size >= 5,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD600), // Amarelo
                    contentColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Seleção de Palavra")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate("jogo") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar para Jogar")
            }
        }
    }
}