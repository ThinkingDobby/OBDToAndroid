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
import kr.rabbito.obdtoandroidwithcompose.OBDToAndroidApplication
import kr.rabbito.obdtoandroidwithcompose.data.OBDRepository
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModel
import kr.rabbito.obdtoandroidwithcompose.viewModel.CarInfoViewModelFactory
import kr.rabbito.obdtoandroidwithcompose.data.SPP_UUID
import kr.rabbito.obdtoandroidwithcompose.ui.theme.OBDToAndroidWithComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obdViewModel by lazy {
            ViewModelProvider(this, CarInfoViewModelFactory(OBDRepository())).get(CarInfoViewModel::class.java)
        }

        setContent {
            // 블루투스 권한 요청
            val permissionState = rememberPermissionState(permission = Manifest.permission.BLUETOOTH_CONNECT)
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }

            OBDToAndroidWithComposeTheme {
                ScannerApp(obdViewModel)
            }
        }

        lifecycleScope.launch {
            var macAddress = OBDToAndroidApplication.getInstance().getDataStore().macAddress.first()
            Log.d("check mac address", macAddress ?: "null")
            OBDToAndroidApplication.getInstance().getDataStore().clearMacAddress()  // 테스트용 - MAC주소 초기화

            if (macAddress == null) {
                // 차량 OBD2 장치의 MAC 주소: "00:1D:A5:02:6E:FB"
                val newMacAddress = "3C:9C:0F:FB:4D:F6" // 다일로그에서 MAC주소 받아오는 부분
                // 추후 변경 고려해 다일로그는 재사용 가능해야

                macAddress = newMacAddress
                OBDToAndroidApplication.getInstance().getDataStore().setMacAddress(newMacAddress)
            }

            obdViewModel.loadDevice(macAddress, SPP_UUID)
            obdViewModel.loadConnection(obdViewModel.device, this@MainActivity)
            obdViewModel.startDataLoading(obdViewModel.connection, this@MainActivity)
        }
    }
}