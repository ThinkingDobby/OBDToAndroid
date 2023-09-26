package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun BarChart(
    progress: Float,
    total: Float,
    color: Color,
    strokeWidth: Dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val maxBarHeight = size.height
        val currentBarHeight = maxBarHeight * (min(progress, total) / total)

        drawRoundRect(
            color = color,
            size = Size(strokeWidth.toPx(), currentBarHeight),
            topLeft = Offset((size.width - strokeWidth.toPx()) / 2, maxBarHeight - currentBarHeight),
            cornerRadius = CornerRadius((4.dp).toPx())
        )
    }
}

@Composable
fun AnimatedBarChart(
    targetProgress: Float,
    total: Float = 360f,
    color: Color = Color.Red,
    strokeWidth: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(targetValue = targetProgress)
    BarChart(
        progress = animatedProgress,
        total = total,
        color = color,
        strokeWidth = strokeWidth,
        modifier = modifier
    )
}