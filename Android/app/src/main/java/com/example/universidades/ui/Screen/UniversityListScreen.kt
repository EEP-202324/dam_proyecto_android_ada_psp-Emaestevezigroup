package com.example.universidades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.universidades.viewmodels.UniversityViewModel
import kotlinx.coroutines.launch


@Composable
fun UniversityListScreen(
    universities: List<University>,
    onUniversitySelected: (University) -> Unit,
    onDeleteUniversityClicked: (University) -> Unit,
    onAddUniversityClicked: () -> Unit,
    universityViewModel: UniversityViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }
    val selectedUniversity = remember { mutableStateOf<University?>(null) }

    LaunchedEffect(key1 = true) {
        universityViewModel.loadUniversities()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.university_list_title),
            modifier = Modifier.padding(bottom = 18.dp),
            style = MaterialTheme.typography.h6
        )
        LazyColumn(
            modifier = Modifier.weight(1f) // Ajusta el LazyColumn para que ocupe todo el espacio disponible
        ) {
            items(universities) { university ->
                Row(
                    modifier = Modifier
                        .padding(18.dp)
                        .background(color = Color.White)
                        .clickable { onUniversitySelected(university) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = university.name,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.weight(1f)
                            .clickable { onUniversitySelected(university) }
                    )
                    IconButton(
                        onClick = {
                            selectedUniversity.value = university
                            dialogState.value = true
                        }
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
        Button(
            onClick = onAddUniversityClicked,
            modifier = Modifier
                .defaultMinSize(minWidth = 0.dp, minHeight = 0.dp)
                .padding(horizontal = 70.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.add_university_button_label))
        }
    }

    // Diálogo de confirmación de eliminación
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = {
                dialogState.value = false
                selectedUniversity.value = null
            },
            title = { Text(text = "Confirmar eliminación") },
            text = { Text(text = "¿Está seguro de que desea eliminar esta universidad?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            onDeleteUniversityClicked(selectedUniversity.value!!)
                            dialogState.value = false
                            selectedUniversity.value = null
                        }
                    }
                ) {
                    Text(text = "Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        selectedUniversity.value = null
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
