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
import kr.rabbito.obdtoandroidwithcompose.ui.theme.smallGaugeTitleStyle
import kr.rabbito.obdtoandroidwithcompose.ui.theme.smallGaugeUnitStyle
import kr.rabbito.obdtoandroidwithcompose.ui.theme.smallGaugeValueStyle

@Composable
fun SmallGauge(title: String, state: State<Int?>, unit: String, total: Int, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .size(width = 75.dp, height = 75.dp),
        contentAlignment = Alignment.Center
    ) {
        val value = state.value ?: 0

        Image(
            painterResource(id = R.drawable.main_iv_gauge_background_small),
            "gauge_background",
            modifier = Modifier.size(width = 71.dp, height = 71.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = smallGaugeTitleStyle
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    value.toString(),
                    style = smallGaugeValueStyle
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    unit,
                    style = smallGaugeUnitStyle
                )
            }
        }

        AnimatedPieChart(
            targetProgress = value.toFloat(),
            total = total.toFloat(),
            color = color,
            modifier = Modifier.size(75.dp)
        )
    }
}