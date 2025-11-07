package com.example.projetoforca.ui.jogo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents // <-- IMPORT CORRETO
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projetoforca.data.local.AppDatabase
import com.example.projetoforca.data.repository.JogoRepository
import com.example.projetoforca.data.repository.RankingRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeJogo(
    navController: NavHostController,
    categoriaSelecionada: String = "Frutas"
) {
    // --- Configuração da Factory ---
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)
    val jogoRepository = JogoRepository(database.palavraDao())
    val rankingRepository = RankingRepository(database.rankingDao())

    // USA A FACTORY (Corrige 'JogoViewModelFactory')
    val factory = JogoViewModelFactory(jogoRepository, rankingRepository)
    // USA O VIEWMODEL (Corrige 'JogoViewModel')
    val viewModel: JogoViewModel = viewModel(factory = factory)
    // --- Fim da Configuração ---

    // USA O UISTATE (Corrige 'uiState')
    val uiState by viewModel.uiState.collectAsState()

    val corFundo = Color(0xFF212121)
    val corTexto = Color.White
    val corTitulo = Color(0xFFFDD835)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogo da Forca", color = corTitulo) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("tela_inicial") {
                            popUpTo(0)
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = corTexto)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("classificacao") }) {
                        Icon(
                            Icons.Default.EmojiEvents, // <-- Corrige 'EmojiEvents'
                            contentDescription = "Ranking",
                            tint = corTitulo
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
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // USA AS VARIÁVEIS DO UISTATE (Corrige 'erros', 'categoria', etc.)
                DesenhoForca(erros = uiState.erros, cor = corTexto)
                Text(
                    text = "Dica: ${uiState.categoria}",
                    color = corTitulo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = uiState.palavraExibida,
                    color = corTexto,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )

                if (uiState.estadoDoJogo == GameState.ERRO_PALAVRA) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "ERRO: Nenhuma palavra encontrada.\n\n" +
                                "Por favor, desinstale e reinstale o app.",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // USA AS VARIÁVEIS E FUNÇÕES (Corrige 'letrasUsadas', 'tentarLetra')
                Teclado(
                    letrasUsadas = uiState.letrasUsadas,
                    estadoDoJogo = uiState.estadoDoJogo,
                    onLetraClick = { letra ->
                        viewModel.tentarLetra(letra)
                    }
                )

                if (uiState.estadoDoJogo == GameState.VENCEU || uiState.estadoDoJogo == GameState.PERDEU) {
                    ResultadoDialog(
                        estado = uiState.estadoDoJogo,
                        palavraSecreta = uiState.palavraSecreta,
                        onJogarNovamente = {
                            viewModel.iniciarJogo(categoriaSelecionada) // Corrige 'iniciarJogo'
                        },
                        onSair = {
                            navController.navigate("tela_inicial") {
                                popUpTo(0)
                            }
                        },
                        onSalvarPontuacao = { nome ->
                            viewModel.salvarPontuacao(nome) // Corrige 'salvarPontuacao'
                            Toast.makeText(context, "Pontuação Salva!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

// ... (Composables 'Teclado', 'ResultadoDialog' e 'DesenhoForca' permanecem iguais) ...
@Composable
fun Teclado(
    letrasUsadas: Set<Char>,
    estadoDoJogo: GameState,
    onLetraClick: (Char) -> Unit
) {
    val alfabeto = ('A'..'Z').toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(alfabeto) { letra ->
            val letraUsada = letrasUsadas.contains(letra)
            Button(
                onClick = { onLetraClick(letra) },
                modifier = Modifier.aspectRatio(1f),
                shape = RoundedCornerShape(8.dp),
                enabled = !letraUsada && estadoDoJogo == GameState.JOGANDO, // Corrige 'JOGANDO'
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDD835),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = letra.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ResultadoDialog(
    estado: GameState,
    palavraSecreta: String,
    onJogarNovamente: () -> Unit,
    onSair: () -> Unit,
    onSalvarPontuacao: (String) -> Unit
) {
    val titulo = if (estado == GameState.VENCEU) "Você Venceu!" else "Você Perdeu!"
    val mensagem = if (estado == GameState.VENCEU) "Parabéns!" else "A palavra era: $palavraSecreta"

    var nomeJogador by remember { mutableStateOf("Jogador") }
    var pontuacaoSalva by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDD835))
                Spacer(modifier = Modifier.height(16.dp))
                Text(mensagem, fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.height(24.dp))

                if (estado == GameState.VENCEU && !pontuacaoSalva) {
                    OutlinedTextField(
                        value = nomeJogador,
                        onValueChange = { nomeJogador = it },
                        label = { Text("Seu nome") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFDD835),
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color(0xFFFDD835),
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = Color(0xFFFDD835)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onSalvarPontuacao(nomeJogador)
                            pontuacaoSalva = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Salvar Pontuação")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row {
                    Button(onClick = onSair, modifier = Modifier.weight(1f)) {
                        Text("Sair")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = onJogarNovamente, modifier = Modifier.weight(1f)) {
                        Text("Jogar de Novo")
                    }
                }
            }
        }
    }
}

@Composable
fun DesenhoForca(erros: Int, cor: Color) {
    val forcaAscii = when (erros) {
        0 -> """
             +---+
             |   |
                 |
                 |
                 |
                 |
           =========
        """
        1 -> """
             +---+
             |   |
             O   |
                 |
                 |
                 |
           =========
        """
        2 -> """
             +---+
             |   |
             O   |
             |   |
                 |
                 |
           =========
        """
        3 -> """
             +---+
             |   |
             O   |
            /|   |
                 |
                 |
           =========
        """
        4 -> """
             +---+
             |   |
             O   |
            /|\  |
                 |
                 |
           =========
        """
        5 -> """
             +---+
             |   |
             O   |
            /|\  |
            /    |
                 |
           =========
        """
        6 -> """
             +---+
             |   |
             O   |
            /|\  |
            / \  |
                 |
           =========
        """
        else -> ""
    }
    Text(
        text = forcaAscii.trimIndent(),
        color = cor,
        fontSize = 18.sp,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        lineHeight = 20.sp,
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .height(200.dp)
    )
}