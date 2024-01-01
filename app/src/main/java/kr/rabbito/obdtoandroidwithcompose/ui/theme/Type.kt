package kr.rabbito.obdtoandroidwithcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.rabbito.obdtoandroidwithcompose.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


// FontFamily
val Pretendard = FontFamily(
    Font(R.font.pretendard_regular)
)

val Roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_medium, FontWeight.Medium)
)


// TextStyle
val largeGaugeValueStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 32.sp,
    color = Black
)

val largeGaugeUnitStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Gray
)

val smallGaugeTitleStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Black
)

val smallGaugeValueStyle = TextStyle(
    fontFamily = Roboto,
    fontSize = 16.sp,
    color = Black
)

val smallGaugeUnitStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 12.sp,
    color = Gray
)

val textGaugeTitleStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Black
)

val textGaugeValueStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    color = Black
)

val textGaugeUnitStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Gray
)

val dialogTitleStyle = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    color = Black
)

val dialogExampleStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 12.sp,
    color = Gray
)

val dialogInputStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Black
)

val dialogInputHintStyle = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    color = Gray
)

val dialogButtonStyle = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    color = Blue
)