package com.example.projetoforca.ui.classificacao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
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
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)
    val repository = RankingRepository(database.rankingDao())
    val factory = ClassificacaoViewModelFactory(repository)
    val viewModel: ClassificacaoViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()
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
                actions = {
                    IconButton(onClick = { navController.navigate("admin") }) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Painel Admin",
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

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

                itemsIndexed(uiState.rankingList) { index, rankingItem ->
                    RankingItemRow(
                        item = rankingItem,
                        rank = index + 1
                    )
                }
            }
        }
    }
}


@Composable
fun RankingItemRow(item: Ranking, rank: Int) {

    val corDestaque = when (rank) {
        1 -> Color(0xFFFDD835)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$rank.",
            color = corDestaque,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = item.playerName,
            color = corDestaque,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
        )

        Text(
            text = "${item.score} pts",
            color = corDestaque,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}