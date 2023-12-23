package kr.rabbito.obdtoandroidwithcompose.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.rabbito.obdtoandroidwithcompose.data.DataStoreRepository
import kr.rabbito.obdtoandroidwithcompose.data.OBDRepository
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModel
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModelFactory
import kr.rabbito.obdtoandroidwithcompose.data.SPP_UUID
import kr.rabbito.obdtoandroidwithcompose.ui.theme.OBDToAndroidWithComposeTheme
import kr.rabbito.obdtoandroidwithcompose.viewModel.SettingInfoViewModel
import kr.rabbito.obdtoandroidwithcompose.viewModel.SettingInfoViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obdViewModel by lazy {
            ViewModelProvider(this, CarInfoViewModelFactory(OBDRepository())).get(CarInfoViewModel::class.java)
        }
        val dataStoreViewModel by lazy {
            ViewModelProvider(this, SettingInfoViewModelFactory(DataStoreRepository(this))).get(SettingInfoViewModel::class.java)
        }

        setContent {
            // 블루투스 권한 요청
            val permissionState = rememberPermissionState(permission = Manifest.permission.BLUETOOTH_CONNECT)
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }

            OBDToAndroidWithComposeTheme {
                ScannerApp(this@MainActivity, obdViewModel, dataStoreViewModel)
            }
        }
    }
}