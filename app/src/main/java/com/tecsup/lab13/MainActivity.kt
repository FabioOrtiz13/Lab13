package com.tecsup.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
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
                // Paso 1: Pantalla principal con Surface
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VisibilityDemo() // Llamamos a nuestro Composable
                }
            }
        }
    }
}

@Composable
fun VisibilityDemo() {
    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { visible = !visible }
        ) {
            Text(text = if (visible) "Ocultar cuadro" else "Mostrar cuadro")
        }
        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),   // Efecto de desvanecerse al aparecer
            exit = fadeOut()    // Efecto de desvanecerse al desaparecer
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color(0xFF00BCD4)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "¡Hola!", color = Color.White)
            }
        }
    }
}
