package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.universidades.R
import com.example.universidades.models.University
import com.example.universidades.ui.components.UniversityItem


@Composable
fun UniversitySearchResultsScreen(
    searchResults: List<University>,
    onUniversitySelected: (University) -> Unit,
    onDeleteUniversityClicked: (University) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = stringResource(R.string.search_results),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(searchResults) { university ->
                UniversityItem(
                    university = university,
                    onUniversitySelected = onUniversitySelected,
                    onDeleteUniversityClicked = onDeleteUniversityClicked // Pasar la función onDeleteUniversityClicked al UniversityItem
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
