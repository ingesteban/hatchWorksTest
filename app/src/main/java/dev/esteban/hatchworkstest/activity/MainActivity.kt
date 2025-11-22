package dev.esteban.hatchworkstest.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.esteban.hatchworkstest.HatchWorksTestApp
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HatchWorksTestTheme {
                HatchWorksTestApp()
            }
        }
    }
}
