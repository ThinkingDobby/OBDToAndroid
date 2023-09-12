package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    progress: Float,
    total: Float = 360f,
    color: Color = Color.Red,
    strokeWidth: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val innerRadius = size.minDimension / 2
        val outerRadius = innerRadius - strokeWidth.toPx()
        val startAngle = 90f
        val sweepAngle = progress / total * 360f

        // cap = StrokeCap.Round를 추가해서 끝 부분을 뭉툭하게 만듭니다.
        val strokeStyle = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = strokeStyle, // 여기에 수정된 스타일을 적용
            size = Size(outerRadius * 2, outerRadius * 2),
            topLeft = Offset((size.width - outerRadius * 2) / 2, (size.height - outerRadius * 2) / 2)
        )
    }
}

@Composable
fun AnimatedPieChart(
    targetProgress: Float,
    total: Float = 360f,
    color: Color = Color.Red,
    strokeWidth: Dp = 5.dp,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(targetValue = targetProgress)
    PieChart(
        progress = animatedProgress,
        total = total,
        color = color,
        strokeWidth = strokeWidth,
        modifier = modifier
    )
}