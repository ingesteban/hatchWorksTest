package dev.esteban.hatchworkstest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.esteban.hatchworkstest.navigation.AppNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HatchWorksTestApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

