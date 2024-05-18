package com.example.universidades.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.universidades.R
import com.example.universidades.models.University

@Composable
fun UniversityItem(
    university: University,
    onUniversitySelected: (University) -> Unit,
    onDeleteUniversityClicked: (University) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUniversitySelected(university) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .clickable { onUniversitySelected(university) }
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = university.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { onDeleteUniversityClicked(university) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}
