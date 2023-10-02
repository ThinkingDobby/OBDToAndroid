package kr.rabbito.obdtoandroidwithcompose.obd

val SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB"   // Serial Port Profile (SPP) UUID

val OBD_RESET = "AT Z\r"    // 장치 리셋
val OBD_ACTIVATE_AUTO_PROTOCOL_SEARCH = "AT SP 0\r" // 자동 프로토콜 검색 활성화

val OBD_RPM = "01 0C\r"
val OBD_RPM_RESPONSE = "41 0C"

val OBD_SPEED = "01 0D\r"
val OBD_SPEED_RESPONSE = "41 0D"

val OBD_MAF = "01 10\r"
val OBD_MAF_RESPONSE = "41 10"

val OBD_THROTTLE_POS = "01 11\r"
val OBD_THROTTLE_POS_RESPONSE = "41 11"

val OBD_ENGINE_LOAD = "01 04\r"
val OBD_ENGINE_LOAD_RESPONSE = "41 04"

val OBD_COOLANT_TEMP = "01 05\r"
val OBD_COOLANT_TEMP_RESPONSE = "41 05"

val OBD_FUEL = "01 2F\r"
val OBD_FUEL_RESPONSE = "41 2F"
