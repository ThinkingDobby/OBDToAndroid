package kr.rabbito.obdtoandroidwithcompose.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.data.SPP_UUID
import kr.rabbito.obdtoandroidwithcompose.ui.component.*
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModel
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Blue
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Green
import kr.rabbito.obdtoandroidwithcompose.ui.theme.LightRed
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Red
import kr.rabbito.obdtoandroidwithcompose.viewModel.SettingInfoViewModel

@Composable
fun ScannerApp(
    context: Context,
    obdViewModel: CarInfoViewModel,
    dataStoreViewModel: SettingInfoViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val macAddress by dataStoreViewModel.macAddress.observeAsState()
        LaunchedEffect(Unit) {
//            dataStoreViewModel.clearMacAddress()
            dataStoreViewModel.loadMacAddress()
            Log.d("check mac address", macAddress ?: "null")
        }

        if (macAddress == null) {
            val newMacAddressState = remember { mutableStateOf("") }
            TextFieldDialog(newMacAddressState, {})

            LaunchedEffect(Unit) {
                // 차량 OBD2 장치의 MAC 주소: "00:1D:A5:02:6E:FB"
//                dataStoreViewModel.saveAndSetMacAddress("3C:9C:0F:FB:4D:F6")
            }
        } else {
            LaunchedEffect(Unit) {
                obdViewModel.loadDevice(macAddress!!, SPP_UUID)
                obdViewModel.loadConnection(obdViewModel.device, context)
                obdViewModel.startDataLoading(obdViewModel.connection, context as MainActivity)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxSize(0.01f))
            LargeGauge(
                R.drawable.main_icon_rpm,
                state = obdViewModel.rpm.observeAsState(),
                "rpm",
                total = 7000,
                color = LightRed
            )

            Spacer(modifier = Modifier.fillMaxSize(0.04f))
            LargeGauge(
                R.drawable.main_icon_speed,
                state = obdViewModel.speed.observeAsState(),
                "km/h",
                total = 240,
                color = Red
            )

            Spacer(modifier = Modifier.fillMaxSize(0.03f))

            Row() {
                SmallGauge(
                    title = "MAF",
                    state = obdViewModel.maf.observeAsState(),
                    unit = "g/s",
                    total = 600,
                    color = Blue
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column() {
                    Spacer(modifier = Modifier.height(24.dp))
                    SmallGauge(
                        title = "스로틀",
                        state = obdViewModel.throttlePos.observeAsState(),
                        unit = "%",
                        total = 100,
                        color = Blue
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                SmallGauge(
                    title = "부하",
                    state = obdViewModel.engineLoad.observeAsState(),
                    unit = "%",
                    total = 100,
                    color = Blue
                )
            }

            Spacer(modifier = Modifier.fillMaxSize(0.1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(45.dp))

                BarGauge(
                    icon = R.drawable.main_icon_coolant_temp,
                    state = obdViewModel.coolantTemp.observeAsState(),
                    total = 60,
                    color = Green
                )
                Spacer(modifier = Modifier.width(32.dp))

                BarGauge(
                    icon = R.drawable.main_icon_fuel,
                    state = obdViewModel.fuel.observeAsState(),
                    total = 100,
                    color = Green
                )
                Spacer(modifier = Modifier.width(15.dp))

                TextGauge(
                    title = "연료 소비율",
                    unit = "L/h",
                    state = obdViewModel.fuelRate.observeAsState()
                )
            }
        }
    }
}


