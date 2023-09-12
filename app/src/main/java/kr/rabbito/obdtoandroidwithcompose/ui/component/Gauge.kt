package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.rabbito.obdtoandroidwithcompose.R

@Composable
fun LargeGauge(state: State<Int?>, total: Int, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp)
    ) {
        val value = state.value ?: 0

        Image(
            painterResource(id = R.drawable.main_iv_gauge_background),
            "",
            modifier = Modifier.size(width = 178.dp, height = 178.dp)
        )

        AnimatedPieChart(
            targetProgress = value.toFloat(),
            total = total.toFloat(),
            color = color,
            modifier = Modifier.size(180.dp)
        )
    }
}