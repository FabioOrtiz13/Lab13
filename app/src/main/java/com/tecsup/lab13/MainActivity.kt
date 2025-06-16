@file:OptIn(ExperimentalAnimationApi::class)

package com.tecsup.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    FullDemo() // Composable integrado con todos los ejercicios
                }
            }
        }
    }
}

@Composable
fun FullDemo() {
    // Estados para visibilidad, color, tamaño, posición y contenido
    var visible by remember { mutableStateOf(false) }
    var isBlue by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var moved by remember { mutableStateOf(false) }
    var screen by remember { mutableStateOf(ScreenState.Loading) }

    // Animación de color
    val animatedColor by animateColorAsState(
        targetValue = if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50),
        animationSpec = tween(durationMillis = 600)
    )
    // Animaciones de tamaño y offset
    val size by animateDpAsState(
        targetValue = if (expanded) 200.dp else 150.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val offsetX by animateDpAsState(
        targetValue = if (moved) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val offsetY by animateDpAsState(
        targetValue = if (moved) 50.dp else 0.dp,
        animationSpec = tween(durationMillis = 600)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Controles para visibilidad y color
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
        // Controles para tamaño y posición
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
        // Controles para estado de contenido
        Row {
            Button(onClick = { screen = ScreenState.Loading }) {
                Text("Cargando")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { screen = ScreenState.Content }) {
                Text("Contenido")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { screen = ScreenState.Error }) {
                Text("Error")
            }
        }
        Spacer(Modifier.height(24.dp))

        // Elemento combinado con visibilidad, color, tamaño y posición
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offsetX, y = offsetY)
                    .size(size)
                    .background(animatedColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Labo 13", color = Color.White)
            }
        }
        Spacer(Modifier.height(24.dp))

        // AnimatedContent para cambio de contenido
        AnimatedContent(
            targetState = screen,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) with
                        fadeOut(animationSpec = tween(500))
            }
        ) { state ->
            when (state) {
                ScreenState.Loading -> Text("Cargando...", style = MaterialTheme.typography.bodyLarge)
                ScreenState.Content -> Text("Aquí está el contenido!", style = MaterialTheme.typography.bodyLarge)
                ScreenState.Error -> Text("¡Ocurrió un error!", color = Color.Red, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
