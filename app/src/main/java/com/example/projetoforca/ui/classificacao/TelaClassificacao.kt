package com.example.projetoforca.ui.classificacao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn // Requisito do PDF
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projetoforca.data.local.AppDatabase
import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.repository.RankingRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaClassificacao(
    navController: NavHostController
) {
    // --- Configuração da Injeção de Dependência (Factory) ---
    // Isto é necessário para atender ao requisito da Factory
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)
    val repository = RankingRepository(database.rankingDao()) // Usando o DAO do DB
    val factory = ClassificacaoViewModelFactory(repository)

    // Inicializa o ViewModel usando a Factory que acabamos de criar
    val viewModel: ClassificacaoViewModel = viewModel(factory = factory)
    // --- Fim da Configuração ---

    // Coleta o UiState do ViewModel de forma reativa
    val uiState by viewModel.uiState.collectAsState()

    // Estilo "Quadro-Negro" que definimos anteriormente
    val corFundo = Color(0xFF212121)
    val corTitulo = Color(0xFFFDD835)
    val corTextoPadrao = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RANKING",
                        color = corTitulo,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = corTextoPadrao
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = corFundo)
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = corFundo
        ) {
            // A "LazyColumn" é obrigatória para listas dinâmicas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Cabeçalho da Lista
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pos.", color = corTextoPadrao, fontWeight = FontWeight.Bold)
                        Text("Jogador", color = corTextoPadrao, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(horizontal = 16.dp))
                        Text("Pontos", color = corTextoPadrao, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Gray))
                }

                // Renderiza a lista de ranking vinda do UiState
                itemsIndexed(uiState.rankingList) { index, rankingItem ->
                    RankingItemRow(
                        item = rankingItem,
                        rank = index + 1 // Posição (1, 2, 3...)
                    )
                }
            }
        }
    }
}

/**
 * Um Composable para desenhar cada linha do ranking.
 */
@Composable
fun RankingItemRow(item: Ranking, rank: Int) {
    // Destaque para o Top 3
    val corDestaque = when (rank) {
        1 -> Color(0xFFFDD835) // Ouro
        2 -> Color(0xFFC0C0C0) // Prata
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Posição
        Text(
            text = "$rank.",
            color = corDestaque,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        // Nome (do Ranking.kt)
        Text(
            text = item.playerName,
            color = corDestaque,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
        )
        // Pontuação (do Ranking.kt)
        Text(
            text = "${item.score} pts",
            color = corDestaque,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}