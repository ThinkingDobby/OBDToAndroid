package kr.rabbito.obdtoandroidwithcompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModel
import kr.rabbito.obdtoandroidwithcompose.ui.component.LargeGauge
import kr.rabbito.obdtoandroidwithcompose.ui.theme.LightRed
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Red

@Composable
fun ScannerApp(viewModel: OBDViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            LargeGauge(R.drawable.main_icon_rpm, state = viewModel.speed.observeAsState(), "rpm", total = 7000, color = LightRed)

            Spacer(modifier = Modifier.height(44.dp))
            LargeGauge(R.drawable.main_icon_speed, state = viewModel.speed.observeAsState(), "km/h", total = 240, color = Red)
        }

        Row(

        ) {

        }
    }
}


