package com.example.universidades.adapter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universidades.models.University

class UniversityAdapter(private val universities: List<University>, private val onItemClick: (University) -> Unit) {

    @Composable
    fun UniversityList() {
        LazyColumn {
            items(universities) { university ->
                UniversityItem(university = university)
            }
        }
    }

    @Composable
    private fun UniversityItem(university: University) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onItemClick(university) }
        ) {
            Column {
                Text(text = university.name)
                Text(text = university.address)
            }
        }
    }
}
