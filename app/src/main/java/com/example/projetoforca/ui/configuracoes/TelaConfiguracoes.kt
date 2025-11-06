package com.example.projetoforca.ui.configuracoes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaConfiguracoes(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), // Fundo preto
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "📖 PASSO A PASSO",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFDD835), // Amarelo vibrante
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Passos do jogo
            val textoAmarelo = Color(0xFFFDD835)

            Text(
                text = "1️⃣ O objetivo é adivinhar a palavra secreta antes de acabar as tentativas.",
                fontSize = 18.sp,
                color = textoAmarelo,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "2️⃣ Cada erro desenha uma parte do boneco. Você tem 6 chances!",
                fontSize = 18.sp,
                color = textoAmarelo,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "3️⃣ Acerte todas as letras antes que o boneco esteja completo.",
                fontSize = 18.sp,
                color = textoAmarelo,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "4️⃣ Complete a palavra e vença o jogo!",
                fontSize = 18.sp,
                color = textoAmarelo,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFDD835))
            ) {
                Text(
                    text = "⬅ VOLTAR",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}