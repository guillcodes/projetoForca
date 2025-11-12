package com.example.projetoforca.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projetoforca.data.local.AppDatabase
import com.example.projetoforca.data.local.Ranking
import com.example.projetoforca.data.repository.RankingRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminRanking(
    navController: NavHostController
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)
    val rankingRepository = RankingRepository(database.rankingDao())
    val factory = AdminRankingViewModelFactory(rankingRepository)
    val viewModel: AdminRankingViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()

    var nomeState by remember { mutableStateOf("") }
    var scoreState by remember { mutableStateOf("") }


    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<Ranking?>(null) }
    var showEditDialog by remember { mutableStateOf<Ranking?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Ranking (CRUD)", color = Color(0xFFFDD835)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1C1C1C))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1C1C1C))
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Adicionar Nova Pontuação", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nomeState,
                onValueChange = { nomeState = it },
                label = { Text("Nome") },
            )
            OutlinedTextField(
                value = scoreState,
                onValueChange = { scoreState = it },
                label = { Text("Score") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                // ... (Cores do OutlinedTextField) ...
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (nomeState.isNotBlank() && scoreState.toIntOrNull() != null) {
                        showCreateDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Gray)

            Text("Ranking Atual", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.rankingList) { ranking ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2E2E))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${ranking.playerName}: ${ranking.score} pts",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                showEditDialog = ranking
                            }) {
                                Icon(Icons.Default.Edit, "Editar", tint = Color(0xFFFDD835)) // Amarelo
                            }
                            IconButton(onClick = {
                                showDeleteDialog = ranking
                            }) {
                                Icon(Icons.Default.Delete, "Apagar", tint = Color(0xFFEF5350)) // Vermelho
                            }
                        }
                    }
                }
            }
        }
    }


    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Confirmar Adição") },
            text = { Text("Deseja adicionar '${nomeState}' com ${scoreState} pontos?") },
            confirmButton = {
                Button(onClick = {
                    val score = scoreState.toIntOrNull()
                    if (nomeState.isNotBlank() && score != null) {
                        viewModel.addRanking(nomeState, score)
                        nomeState = ""
                        scoreState = ""
                    }
                    showCreateDialog = false
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) { Text("Cancelar") }
            }
        )
    }

    if (showDeleteDialog != null) {
        val itemParaDeletar = showDeleteDialog!!
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza que deseja apagar '${itemParaDeletar.playerName}' (${itemParaDeletar.score} pts)?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteRanking(itemParaDeletar)
                    showDeleteDialog = null
                }) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Cancelar") }
            }
        )
    }

    // 3. Diálogo de EDITAR
    if (showEditDialog != null) {
        var nomeEditState by remember { mutableStateOf(showEditDialog!!.playerName) }
        var scoreEditState by remember { mutableStateOf(showEditDialog!!.score.toString()) }

        AlertDialog(
            onDismissRequest = { showEditDialog = null },
            title = { Text("Editar Pontuação") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nomeEditState,
                        onValueChange = { nomeEditState = it },
                        label = { Text("Nome") }
                    )
                    OutlinedTextField(
                        value = scoreEditState,
                        onValueChange = { scoreEditState = it },
                        label = { Text("Score") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val score = scoreEditState.toIntOrNull()
                    if (nomeEditState.isNotBlank() && score != null) {
                        val itemAtualizado = showEditDialog!!.copy(
                            playerName = nomeEditState,
                            score = score
                        )
                        viewModel.updateRanking(itemAtualizado)
                    }
                    showEditDialog = null
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = null }) { Text("Cancelar") }
            }
        )
    }
}