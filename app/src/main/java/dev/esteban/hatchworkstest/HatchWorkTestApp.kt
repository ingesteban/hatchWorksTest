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

// | Hero Movie (backdrop grande, título, descripción)
//GET /movie/now_playing

// GET /trending/movie/day
// GET /movie/popular
// GET /movie/top_rated
// GET /movie/now_playing
// GET /movie/upcoming

// GET /genre/movie/list
// GET /discover/movie?with_genres={id}

// GET /trending/movie/week
// GET /search/movie?query=a

// Categorías por sección
// https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1
