package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoviesScreen(goToDetail: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Text("Movie Screen")

        Button(onClick = { goToDetail("1") }) {
            Text("Navigate to detail")
        }
    }
}
