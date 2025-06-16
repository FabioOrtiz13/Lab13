package com.tecsup.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VisibilityColorSizeDemo() // Demo combinado
                }
            }
        }
    }
}

@Composable
fun VisibilityColorSizeDemo() {
    // Estados para visibilidad, color, tamaño y posición
    var visible by remember { mutableStateOf(false) }
    var isBlue by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var moved by remember { mutableStateOf(false) }

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
        // Controles
        Row {
            Button(onClick = { visible = !visible }) {
                Text(text = if (visible) "Ocultar cuadro" else "Mostrar cuadro")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isBlue = !isBlue }) {
                Text(text = if (isBlue) "Ver verde" else "Ver azul")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { expanded = !expanded }) {
                Text(text = if (expanded) "Encoger" else "Expandir")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { moved = !moved }) {
                Text(text = if (moved) "Volver" else "Mover")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

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
    }
}
