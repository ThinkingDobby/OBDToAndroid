package kr.rabbito.obdtoandroidwithcompose.data

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kr.rabbito.obdtoandroidwithcompose.data.entity.Connection
import kr.rabbito.obdtoandroidwithcompose.data.entity.Device
import kr.rabbito.obdtoandroidwithcompose.obd.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.math.max

interface Repository {
    fun getDevice(address: String, uuid: String): Device
    suspend fun connectToDevice(device: Device?, context: Context): Connection?
    suspend fun getResponse(connection: Connection?, command: String): Array<Int?>?
}

class OBDRepository : Repository {
    override fun getDevice(address: String, uuid: String): Device {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
        val uuid = UUID.fromString(uuid)

        return Device(bluetoothDevice, address, uuid)
    }

    override suspend fun connectToDevice(device: Device?, context: Context): Connection? {
        if (device == null) {
            Log.e("INIT_ERROR", "Device not set")

            return null
        }

        var socket: BluetoothSocket? = null

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED) return null

        socket = device.bluetoothDevice.createRfcommSocketToServiceRecord(device.uuid)
        connectSocket(socket, context)
        resetDevice(socket.inputStream, socket.outputStream)

        return Connection(socket, socket.inputStream, socket.outputStream)
    }

    override suspend fun getResponse(connection: Connection?, command: String): Array<Int?>? {
        if (connection == null) {
            Log.e("INIT_ERROR", "Connection not set")

            return null
        }

        val response = sendCommand(connection.inputStream, connection.outputStream, command)

        val cleanedResponse = response.replace("\r", "").replace("\n", " ").replace(">", "")
        val dataFields = cleanedResponse.split(" ")

        Log.d("check cleanedResponse", cleanedResponse)
        if (dataFields.size < 4) return null

        val code = dataFields[2] + " " + dataFields[3]
        Log.d("check code", code)

        when (code) {
            OBD_RPM_RESPONSE -> return arrayOf(0, parseRPM(response, dataFields))
            OBD_SPEED_RESPONSE -> return arrayOf(1, parseSpeed(response, dataFields))
            OBD_MAF_RESPONSE -> return arrayOf(2, parseMAF(response, dataFields))
            OBD_THROTTLE_POS_RESPONSE -> return arrayOf(3, parseThrottlePos(response, dataFields))
            OBD_ENGINE_LOAD_RESPONSE -> return arrayOf(4, parseEngineLoad(response, dataFields))
            OBD_COOLANT_TEMP_RESPONSE -> return arrayOf(5, parseCoolantTemp(response, dataFields))
            OBD_FUEL_RESPONSE -> return arrayOf(6, parseFuel(response, dataFields))
            else -> return null
        }
    }
}

private suspend fun connectSocket(socket: BluetoothSocket?, context: Context) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED) return

    var isConnected = false
    while (!isConnected) {
        try {
            socket?.connect()
            isConnected = true
        } catch (e: IOException) {
            // 연결에 실패한 경우, 일정 시간 대기 후 다시 시도
            delay(5000) // 5초 대기
//            Log.d("check connection", "waiting...")
        }
    }
}

private suspend fun resetDevice(inputStream: InputStream?, outputStream: OutputStream?) {
    if (inputStream == null || outputStream == null) {
        Log.e("INIT_ERROR", "Socket not set")

        return
    }

    sendCommand(inputStream, outputStream, OBD_RESET) // 장치 리셋
    sendCommand(inputStream, outputStream, OBD_ACTIVATE_AUTO_PROTOCOL_SEARCH) // 자동 프로토콜 검색 활성화
}

private suspend fun sendCommand(
    inputStream: InputStream,
    outputStream: OutputStream,
    command: String
): String {
    val sendData = command.toByteArray()

    withContext(Dispatchers.IO) {
        try {
            outputStream.write(sendData)
        } catch (e: IOException) {
            Log.e("OBD_ERROR", "write: Disconnected by obd")
        }
    }
    withContext(Dispatchers.IO) {
        try {
            outputStream.flush()
        } catch (e: IOException) {
            Log.e("OBD_ERROR", "flush: Disconnected by obd")
        }
    }

    val buffer = ByteArray(1024)
    val bytesRead = withContext(Dispatchers.IO) {
        try {
            inputStream.read(buffer)
        } catch (e: IOException) {
            Log.e("OBD_ERROR", "read: Disconnected by obd")
        }
    }

//    Log.d("check command return", String(buffer, 0, bytesRead).trim())
    return String(buffer, 0, bytesRead).trim()
}

fun parseSpeed(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_SPEED_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 5) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[4].replace(">", "")
//    Log.d("check dataFields", "${hexResult}")
    return hexResult.toInt(16)
}

fun parseRPM(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_RPM_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 6) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = (dataFields[4] + dataFields[5]).replace(">", "")
//    Log.d("check dataFields", "${dataFields[3]} ${dataFields[4]}")
    return (hexResult.toInt(16).toDouble() / 4).toInt()
}

fun parseMAF(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_RPM_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 6) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = (dataFields[4] + dataFields[5]).replace(">", "")
//    Log.d("check dataFields", "${dataFields[3]} ${dataFields[4]}")
    return (hexResult.toInt(16).toDouble() / 100).toInt()
}

fun parseThrottlePos(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_THROTTLE_POS_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 5) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[4].replace(">", "")
    Log.d("check dataFields", "${hexResult}")
    return (hexResult.toInt(16).toDouble() / 255 * 100).toInt()
}

fun parseEngineLoad(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_ENGINE_LOAD_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 5) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[4].replace(">", "")
//    Log.d("check dataFields", "${hexResult}")
    return (hexResult.toInt(16) / 255) * 100
}

fun parseCoolantTemp(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_COOLANT_TEMP_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 5) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[4].replace(">", "")
//    Log.d("check dataFields", "${hexResult}")
    return max(hexResult.toInt(16) - 43, 0)
}

fun parseFuel(response: String?, dataFields: List<String>): Int? {
    if (response == null) {
        Log.e("PARSE_FUEL_ERROR", "Empty response")

        return null
    }

    if (dataFields.size < 5) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[4].replace(">", "")
//    Log.d("check dataFields", "${hexResult}")
    return (hexResult.toInt(16).toDouble() / 255 * 100).toInt()
}