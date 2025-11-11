
package com.example.projetoforca.ui.telaInicial

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaInicial(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF212121)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = { navController.navigate("configuracoes") },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configurações",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.weight(2f))

                Text(
                    text = buildAnnotatedString {
                        append("JOGO DA\n")
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append("FORCA")
                        }
                    },
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFDD835),
                    textAlign = TextAlign.Center,
                    lineHeight = 50.sp
                )

                Spacer(modifier = Modifier.weight(1.5f))

                BotaoAmareloEstilizado(
                    texto = "JOGAR",
                    onClick = {
                        if (isLoggedIn) {

                            navController.navigate("jogo")
                        } else {

                            navController.navigate("login/cadastro")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                BotaoAmareloEstilizado(
                    texto = "RANKING",
                    onClick = { navController.navigate("classificacao") }
                )


                if (isLoggedIn) {
                    Spacer(modifier = Modifier.height(20.dp))
                    BotaoVermelhoEstilizado(
                        texto = "SAIR",
                        onClick = onLogout
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
private fun BotaoAmareloEstilizado(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFDD835), Color(0xFFFBC02D))
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(2.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texto,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun BotaoVermelhoEstilizado(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE53935), Color(0xFFC62828))
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(2.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texto,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}