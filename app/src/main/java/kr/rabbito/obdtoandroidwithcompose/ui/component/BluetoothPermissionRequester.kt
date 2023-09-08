package kr.rabbito.obdtoandroidwithcompose.util

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BluetoothPermissionRequester(onPermissionGranted: () -> Unit) {
    val bluetoothPermissionState = rememberPermissionState(android.Manifest.permission.BLUETOOTH_CONNECT)

    when {
        bluetoothPermissionState.hasPermission -> {
            onPermissionGranted()
        }
        bluetoothPermissionState.shouldShowRationale || !bluetoothPermissionState.permissionRequested -> {
            // 권한이 거절되었을 때의 UI나 액션을 여기에 표시하거나 처리합니다.
            // 예: 사용자에게 권한이 필요한 이유를 설명하는 대화 상자를 표시
            Button(onClick = { bluetoothPermissionState.launchPermissionRequest() }) {
                Text("블루투스 권한 요청")
            }
        }
        else -> {
            // 사용자가 권한을 거절하고 "다시 묻지 않음"을 선택한 경우의 처리
            // 예: 설정 화면으로 유도하는 버튼 등을 표시
        }
    }
}