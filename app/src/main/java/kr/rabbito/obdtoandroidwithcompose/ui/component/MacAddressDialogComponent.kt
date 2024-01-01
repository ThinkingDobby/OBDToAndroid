package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import kr.rabbito.obdtoandroidwithcompose.ui.theme.Blue
import kr.rabbito.obdtoandroidwithcompose.ui.theme.dialogExampleStyle
import kr.rabbito.obdtoandroidwithcompose.ui.theme.dialogInputHintStyle
import kr.rabbito.obdtoandroidwithcompose.ui.theme.dialogTitleStyle

@Composable
fun MacAddressDialogTitle() {
    Column() {
        Row() {
            Text(text = "OBD 스캐너", style = dialogTitleStyle)
            Text(text = "의", style = dialogTitleStyle, fontWeight = FontWeight.Normal)
        }

        Row() {
            Text(text = "MAC 주소", style = dialogTitleStyle, color = Blue)
            Text(
                text = "를 입력하세요.",
                style = dialogTitleStyle,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun MacAddressDialogSmallText() {
    Text(text = "예) 0A:1B:2C:D3:E4:F5", style = dialogExampleStyle)
}

@Composable
fun MacAddressDialogPlaceholder() {
    Text("':' 을 포함해 입력하세요.", style = dialogInputHintStyle)
}