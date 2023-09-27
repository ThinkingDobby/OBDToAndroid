package kr.rabbito.obdtoandroidwithcompose.obd

val SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB"   // Serial Port Profile (SPP) UUID

val OBD_RESET = "AT Z\r"    // 장치 리셋
val OBD_ACTIVATE_AUTO_PROTOCOL_SEARCH = "AT SP 0\r" // 자동 프로토콜 검색 활성화

val OBD_SPEED = "01 0D\r"
val OBD_SPEED_RESPONSE = "41 0D"

val OBD_RPM = "01 0C\r"
val OBD_RPM_RESPONSE = "41 0C"

val OBD_COOLANT_TEMP = "01 05\r"
val OBD_COOLANT_TEMP_RESPONSE = "41 05"
