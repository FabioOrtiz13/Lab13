package com.tecsup.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tecsup.lab13.ui.theme.Lab13Theme

// Estados para AnimatedContent
private enum class ScreenState { Loading, Content, Error }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Ejercicio() // Composable integrado con todos los ejercicios
                }
            }
        }
    }
}

@Composable
fun Ejercicio() {
    // Estados para visibilidad, color, tamaño y posición
    var visible by remember { mutableStateOf(false) }
    var isBlue by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var moved by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    // Animaciones
    val animatedColor by animateColorAsState(
        targetValue = if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50),
        animationSpec = tween(durationMillis = 600)
    )
    val size by animateDpAsState(
        targetValue = if (expanded) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val offsetX by animateDpAsState(
        targetValue = if (moved) 0.dp else 0.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val offsetY by animateDpAsState(
        targetValue = if (moved) (-400).dp else 0.dp,
        animationSpec = tween(durationMillis = 600)
    )


    // Modos claro/oscuro
    val background by animateColorAsState(
        targetValue = if (isDarkMode) Color.DarkGray else Color.White,
        animationSpec = tween(durationMillis = 800)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isDarkMode) Color.White else Color.DarkGray,
        animationSpec = tween(durationMillis = 800)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botones de control
            Row {
                Button(onClick = { visible = !visible }) {
                    Text(text = if (visible) "Ocultar cuadro" else "Mostrar cuadro")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { isBlue = !isBlue }) {
                    Text(text = if (isBlue) "Ver verde" else "Ver azul")
                }
            }
            Spacer(Modifier.height(8.dp))
            Row {
                Button(onClick = { expanded = !expanded }) {
                    Text(text = if (expanded) "Encoger" else "Expandir")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { moved = !moved }) {
                    Text(text = if (moved) "Volver" else "Mover")
                }
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { isDarkMode = !isDarkMode }) {
                Text(text = if (isDarkMode) "Modo Claro" else "Modo Oscuro")
            }

            Spacer(Modifier.height(24.dp))

            // AnimatedVisibility para el cuadro y la imagen
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(animationSpec = tween(500))
            ) {
                // Estilo profesional: Card con esquinas redondeadas y sombra
                Card(
                    modifier = Modifier
                        .offset(x = offsetX, y = offsetY)
                        .size(size),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = animatedColor)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Imagen transparente que escala con el tamaño del Card
                        Image(
                            painter = painterResource(R.drawable.grizz),
                            contentDescription = "Griz",
                            modifier = Modifier.size(size * 0.7f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Lab 13",
                            color = contentColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
