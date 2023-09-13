package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.ui.theme.largeGaugeUnitStyle
import kr.rabbito.obdtoandroidwithcompose.ui.theme.largeGaugeValueStyle

@Composable
fun LargeGauge(icon: Int, state: State<Int?>, unit: String, total: Int, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp),
        contentAlignment = Alignment.Center
    ) {
        val value = state.value ?: 0

        Image(
            painterResource(id = R.drawable.main_iv_gauge_background),
            "gauge_background",
            modifier = Modifier.size(width = 176.dp, height = 176.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = icon),
                "gauge_icon",
                modifier = Modifier.size(width = 32.dp, height = 32.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                value.toString(),
                style = largeGaugeValueStyle
            )

            Text(
                unit,
                style = largeGaugeUnitStyle
            )
            
            Spacer(modifier = Modifier.height(20.dp))
        }

        AnimatedPieChart(
            targetProgress = value.toFloat(),
            total = total.toFloat(),
            color = color,
            modifier = Modifier.size(180.dp)
        )
    }
}