package kr.rabbito.obdtoandroidwithcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.rabbito.obdtoandroidwithcompose.data.OBDRepository
import kr.rabbito.obdtoandroidwithcompose.data.Repository
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModel
import kr.rabbito.obdtoandroidwithcompose.obd.OBDViewModelFactory
import kr.rabbito.obdtoandroidwithcompose.obd.SPP_UUID
import kr.rabbito.obdtoandroidwithcompose.ui.theme.OBDToAndroidWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by lazy {
            ViewModelProvider(this, OBDViewModelFactory(OBDRepository())).get(OBDViewModel::class.java)
        }

        setContent {
            OBDToAndroidWithComposeTheme {
                ScannerApp(viewModel)
            }
        }

        lifecycleScope.launch {
            viewModel.loadDevice("3C:9C:0F:FB:4D:F6", SPP_UUID)
            viewModel.loadConnection(viewModel.device!!, this@MainActivity)
            viewModel.startSpeedLoading(viewModel.connection!!)
        }
    }
}