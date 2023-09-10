package kr.rabbito.obdtoandroidwithcompose.ui

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kr.rabbito.obdtoandroidwithcompose.ui.component.AnimatedPieChart

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview(showSystemUi = true)
fun ScannerApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        // 블루투스 권한 요청
        val permissionState = rememberPermissionState(permission = Manifest.permission.BLUETOOTH_CONNECT)
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }



        val progress = remember { mutableStateOf(100f) }

        AnimatedPieChart(
            targetProgress = progress.value,
            color = Color.Red,
            strokeWidth = 6.dp,
            modifier = Modifier.size(180.dp)
        )
    }
}