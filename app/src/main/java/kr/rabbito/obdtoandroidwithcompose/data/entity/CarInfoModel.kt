package kr.rabbito.obdtoandroidwithcompose.data.entity

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

data class Device (
    val bluetoothDevice: BluetoothDevice,
    val address: String,
    val uuid: UUID
)

data class Connection (
    val socket: BluetoothSocket,
    val inputStream: InputStream,
    val outputStream: OutputStream
)