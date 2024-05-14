package com.example.universidades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.universidades.ui.theme.UniversidadesTheme
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel


class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            UniversidadesTheme {
                WelcomeScreen(navController = navController)
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    var continueClicked by remember { mutableStateOf(false) }

    if (continueClicked) {
        LaunchedEffect(Unit) {
            navController.navigate("primero")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        WelcomeContent(onContinueClick = {
            continueClicked = true
        })
    }
}



@Composable
fun WelcomeContent(onContinueClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a Aula",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                onContinueClick()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text = "Continuar")
        }
    }
}



