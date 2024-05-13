package com.example.universidades.ui

import androidx.compose.material.MaterialTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universidades.models.University
import com.example.universidades.ui.theme.UniversidadesTheme
import com.example.universidades.viewmodels.UniversityViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PrimeroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversidadesTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Lista de universidades",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h4
                    )
                    UniversityList()
                }
            }
        }
    }
}

@Composable
fun UniversityList(viewModel: UniversityViewModel = viewModel()) {
    val universities by viewModel.universities.collectAsState(initial = emptyList())

    LazyColumn {
        items(universities) { university ->
            UniversityItem(university)
        }
    }
}


@Composable
fun UniversityItem(university: University) {
    Text(
        text = university.name,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrimeroActivity()
}
