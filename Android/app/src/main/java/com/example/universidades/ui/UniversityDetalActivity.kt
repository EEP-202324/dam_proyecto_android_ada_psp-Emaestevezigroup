package com.example.universidades.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.universidades.ui.theme.UniversidadesTheme

class UniversityDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversidadesTheme {
                Column {
                    UniversityDetailContent()
                }
            }
        }
    }
}


@Composable
fun UniversityDetailContent() {
    Text(text = "University Detail")
}

