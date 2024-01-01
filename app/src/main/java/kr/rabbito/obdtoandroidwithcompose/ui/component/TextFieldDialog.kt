package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.ui.theme.*

@Composable
fun TextFieldDialog(textState: MutableState<String>) {
    Dialog(
        onDismissRequest = {},
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.2f)    // 모달 배경 투명도 조절

        Box(
            modifier = Modifier
                .paint(painterResource(id = R.drawable.main_iv_dialog_background))
                .size(width = 276.dp, height = 234.dp),
        ) {
            Column(
                modifier = Modifier.padding(top = 35.dp, start = 38.dp)
            ) {
                Row() {
                    Text(text = "OBD 스캐너", style = dialogTitleStyle)
                    Text(text = "의", style = dialogTitleStyle, fontWeight = FontWeight.Normal)
                }

                Row() {
                    Text(text = "MAC 주소", style = dialogTitleStyle, color = Blue)
                    Text(text = "를 입력하세요.", style = dialogTitleStyle, fontWeight = FontWeight.Normal)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "예) 0A:1B:2C:D3:E4:F5", style = dialogExampleStyle)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.main_tf_dialog),
                        contentDescription = "dialog_input",
                    )

                    TextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            textColor = Black
                        ),
                        value = textState.value,
                        onValueChange = { textState.value = it },
                        modifier = Modifier.size(width = 200.dp, height = 50.dp),
                        textStyle = dialogInputStyle,
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}