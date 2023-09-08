package kr.rabbito.obdtoandroidwithcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kr.rabbito.obdtoandroidwithcompose.ui.component.ScannerApp
import kr.rabbito.obdtoandroidwithcompose.ui.theme.OBDToAndroidWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OBDToAndroidWithComposeTheme {
                ScannerApp()
            }
        }
    }
}