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

interface Repository {
    fun getDevice(address: String, uuid: String): Device
    suspend fun connectToDevice(device: Device, context: Context): Connection?
    suspend fun getSpeed(connection: Connection): Int?
}

class OBDRepository : Repository {
    override fun getDevice(address: String, uuid: String): Device {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
        val uuid = UUID.fromString(uuid)

        return Device(bluetoothDevice, address, uuid)
    }

    override suspend fun connectToDevice(device: Device, context: Context): Connection? {
        var socket: BluetoothSocket? = null

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED) return null

        socket = device.bluetoothDevice.createRfcommSocketToServiceRecord(device.uuid)
        connectSocket(socket, context)
        resetDevice(socket.inputStream, socket.outputStream)

        return Connection(socket, socket.inputStream, socket.outputStream)
    }

    override suspend fun getSpeed(connection: Connection): Int? {
        val speedResponse = sendCommand(connection.inputStream, connection.outputStream, OBD_SPEED)

        if (!speedResponse.contains(OBD_SPEED_RESPONSE)) {
            Log.e("OBD_ERROR", "Invalid response for speed command: $speedResponse")

            return -1
        }

        val speed = parseSpeed(speedResponse)
        Log.d("check speed", "Current speed: $speed km/h")

        return speed
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
            Log.d("check connection", "waiting...")
        }
    }
}

private suspend fun resetDevice(inputStream: InputStream?, outputStream: OutputStream?) {
    if (inputStream != null && outputStream != null) {
        sendCommand(inputStream, outputStream, OBD_RESET) // 장치 리셋
        sendCommand(inputStream, outputStream, OBD_ACTIVATE_AUTO_PROTOCOL_SEARCH) // 자동 프로토콜 검색 활성화
    }
}

private suspend fun sendCommand(
    inputStream: InputStream,
    outputStream: OutputStream,
    command: String
): String {
    val sendData = command.toByteArray()
    withContext(Dispatchers.IO) {
        outputStream.write(sendData)
    }
    withContext(Dispatchers.IO) {
        outputStream.flush()
    }
    delay(500)

    val buffer = ByteArray(1024)
    val bytesRead = withContext(Dispatchers.IO) {
        inputStream.read(buffer)
    }

    return String(buffer, 0, bytesRead).trim()
}

private fun parseSpeed(response: String): Int? {
    val cleanedResponse = response.replace("\r", "").replace("\n", "").replace(">", "")
    val dataFields = cleanedResponse.split(" ")

    if (dataFields.size < 4) {
        Log.e("OBD_ERROR", "Insufficient data fields in response: $response")
        return null
    }

    val hexResult = dataFields[3].replace(">", "")
    Log.d("check dataFields", "${hexResult}")
    return hexResult.toInt(16)
}