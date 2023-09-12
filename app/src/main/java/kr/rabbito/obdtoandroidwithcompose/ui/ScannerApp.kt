package kr.rabbito.obdtoandroidwithcompose.ui

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModel
import kr.rabbito.obdtoandroidwithcompose.ui.component.LargeGauge
import kr.rabbito.obdtoandroidwithcompose.ui.theme.LightRed
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Red

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerApp(viewModel: OBDViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        // 블루투스 권한 요청
        val permissionState = rememberPermissionState(permission = Manifest.permission.BLUETOOTH_CONNECT)
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            LargeGauge(state = viewModel.speed.observeAsState(), total = 7000, color = LightRed)

            Spacer(modifier = Modifier.height(44.dp))
            LargeGauge(state = viewModel.speed.observeAsState(), total = 240, color = Red)
        }
    }
}


