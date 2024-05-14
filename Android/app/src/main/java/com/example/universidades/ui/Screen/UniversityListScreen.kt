package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.universidades.R
import com.example.universidades.models.University

@Composable
fun UniversityListScreen(
    universities: List<University>,
    onUniversitySelected: (String) -> Unit,
    onAddUniversityClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.university_list_title),
            modifier = Modifier.padding(bottom = 18.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = universities.size) { index ->
                val university = universities[index]
                UniversityListItem(university = university, onClick = onUniversitySelected)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onAddUniversityClicked,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(id = R.string.add_university_button_label))
        }
    }
}

@Composable
fun UniversityListItem(university: University, onClick: (String) -> Unit) {
    Button(onClick = { onClick(university.name) }) {
        Text(text = university.name)
    }
}
