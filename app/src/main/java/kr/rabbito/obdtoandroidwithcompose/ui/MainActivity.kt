package kr.rabbito.obdtoandroidwithcompose.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import kr.rabbito.obdtoandroidwithcompose.data.OBDRepository
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModel
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModelFactory
import kr.rabbito.obdtoandroidwithcompose.obd.SPP_UUID
import kr.rabbito.obdtoandroidwithcompose.ui.theme.OBDToAndroidWithComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by lazy {
            ViewModelProvider(this, OBDViewModelFactory(OBDRepository())).get(OBDViewModel::class.java)
        }

        setContent {
            // 블루투스 권한 요청
            val permissionState = rememberPermissionState(permission = Manifest.permission.BLUETOOTH_CONNECT)
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }

            OBDToAndroidWithComposeTheme {
                ScannerApp(viewModel)
            }
        }

        lifecycleScope.launch {
            viewModel.loadDevice("3C:9C:0F:FB:4D:F6", SPP_UUID) // 차량 OBD2 장치의 MAC 주소: "00:1D:A5:02:6E:FB"
            viewModel.loadConnection(viewModel.device, this@MainActivity)
            viewModel.startDataLoading(viewModel.connection, this@MainActivity)
        }
    }
}