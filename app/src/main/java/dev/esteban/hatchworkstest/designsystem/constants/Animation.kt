package dev.esteban.hatchworkstest.designsystem.constants

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing

object Animation {
    val Fast = 150
    val Medium = 300
    val Slow = 600

    val EaseInOut = FastOutSlowInEasing
    val EaseOut = LinearOutSlowInEasing
    val EaseIn = FastOutLinearInEasing
}