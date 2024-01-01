package kr.rabbito.obdtoandroidwithcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import kr.rabbito.obdtoandroidwithcompose.R
import kr.rabbito.obdtoandroidwithcompose.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun TextFieldDialog(
    title: @Composable () -> Unit,
    smallText: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    inputStyle: TextStyle,
    buttonStyle: TextStyle,
    textState: MutableState<String>,
    onConfirmation: () -> Unit)
{
    Dialog(
        onDismissRequest = { },
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.2f)    // 모달 배경 투명도 조절

        Box(
            modifier = Modifier
                .paint(painterResource(id = R.drawable.main_iv_dialog_background))
                .size(width = 276.dp, height = 234.dp),
        ) {
            Column(
                modifier = Modifier.padding(top = 32.dp, start = 38.dp)
            ) {
                title()

                Spacer(modifier = Modifier.height(8.dp))
                smallText()

                Spacer(modifier = Modifier.height(13.dp))

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
                        textStyle = inputStyle,
                        singleLine = true,
                        placeholder = placeholder
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onConfirmation() },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(width = 24.dp, height = 18.dp)
                    ) {
                        Text("확인", style = buttonStyle)
                    }
                    
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}