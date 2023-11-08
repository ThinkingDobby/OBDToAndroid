package kr.rabbito.obdtoandroidwithcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModel
import kr.rabbito.obdtoandroidwithcompose.ui.component.BarGauge
import kr.rabbito.obdtoandroidwithcompose.ui.component.LargeGauge
import kr.rabbito.obdtoandroidwithcompose.ui.component.SmallGauge
import kr.rabbito.obdtoandroidwithcompose.ui.component.TextGauge
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Blue
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Green
import kr.rabbito.obdtoandroidwithcompose.ui.theme.LightRed
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Red

@Composable
fun ScannerApp(viewModel: CarInfoViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxSize(0.01f))
            LargeGauge(R.drawable.main_icon_rpm, state = viewModel.rpm.observeAsState(), "rpm", total = 7000, color = LightRed)

            Spacer(modifier = Modifier.fillMaxSize(0.04f))
            LargeGauge(R.drawable.main_icon_speed, state = viewModel.speed.observeAsState(), "km/h", total = 240, color = Red)

            Spacer(modifier = Modifier.fillMaxSize(0.03f))

            Row() {
                SmallGauge(title = "MAF", state = viewModel.maf.observeAsState(), unit = "g/s", total = 600, color = Blue)
                Spacer(modifier = Modifier.width(16.dp))
                
                Column() {
                    Spacer(modifier = Modifier.height(24.dp))
                    SmallGauge(title = "스로틀", state = viewModel.throttlePos.observeAsState(), unit = "%", total = 100, color = Blue)
                }
                Spacer(modifier = Modifier.width(16.dp))
                
                SmallGauge(title = "부하", state = viewModel.engineLoad.observeAsState(), unit = "%", total = 100, color = Blue)
            }

            Spacer(modifier = Modifier.fillMaxSize(0.1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(45.dp))

            BarGauge(icon = R.drawable.main_icon_coolant_temp, state = viewModel.coolantTemp.observeAsState(), total = 60, color = Green)
                Spacer(modifier = Modifier.width(32.dp))
                
                BarGauge(icon = R.drawable.main_icon_fuel, state = viewModel.fuel.observeAsState(), total = 100, color = Green)
                Spacer(modifier = Modifier.width(15.dp))

                TextGauge(title = "연료 소비율", unit = "L/h", state = viewModel.fuelRate.observeAsState())
            }
        }
    }
}


