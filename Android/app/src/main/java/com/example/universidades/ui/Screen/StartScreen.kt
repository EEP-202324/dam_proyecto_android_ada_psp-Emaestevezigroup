package com.example.universidades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.universidades.R

@Composable
fun StartScreen(onStartButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.start_screen_title))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onStartButtonClicked) {
            Text(text = stringResource(R.string.start_button_label))
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(onStartButtonClicked = {})
}