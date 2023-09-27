package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.rabbito.obdtoandroidwithcompose.R

@Composable
fun BarGauge(icon: Int, state: State<Int?>, total: Int, color: androidx.compose.ui.graphics.Color) {
    Column(
        modifier = Modifier
            .size(width = 22.dp, height = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val value = state.value ?: 0

        Box() {
            Image(
                painterResource(id = R.drawable.main_iv_gauge_background_long),
                "gauge_background",
                modifier = Modifier.size(width = 8.dp, height = 90.dp)
            )
            AnimatedBarChart(
                targetProgress = value.toFloat(),
                total = total.toFloat(),
                color = color,
                modifier = Modifier.size(width = 8.dp, height = 90.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painterResource(id = icon),
            "gauge background",
            modifier = Modifier.size(22.dp)
        )
    }
}