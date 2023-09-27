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
import kr.rabbito.obdtoandroidwithcompose.ui.theme.*

@Composable
fun TextGauge(title: String, unit: String, state: State<Int?>) {
    val value = state.value ?: 0

    Column() {
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(id = R.drawable.main_iv_fuel_eff_background),
                "gauge_background",
                modifier = Modifier.size(width = 186.dp, height = 140.dp)
            )

            Column(
            ) {
                Text(
                    title,
                    style = textGaugeTitleStyle
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row(
                        modifier = Modifier.width(28.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            value.toString(),
                            style = textGaugeValueStyle,
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        unit,
                        style = textGaugeUnitStyle,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}