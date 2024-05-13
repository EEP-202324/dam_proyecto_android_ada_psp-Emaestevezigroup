package com.example.universidades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universidades.models.University
import com.example.universidades.ui.theme.UniversidadesTheme
import com.example.universidades.viewmodels.UniversityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversidadesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UniversityScreen()
                }
            }
        }
    }
}

@Composable
fun UniversityScreen(viewModel: UniversityViewModel = viewModel()) {
    val universities by viewModel.universities.collectAsState(initial = emptyList())
    LazyColumn {
        item {
            Text(
                text = "Lista de universidades",
                style = MaterialTheme.typography.h4
            )
        }
        items(universities) { university ->
            UniversityItem(university)
        }
    }
}


@Composable
fun UniversityItem(university: University) {
    Text(text = university.name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainActivity()
}
