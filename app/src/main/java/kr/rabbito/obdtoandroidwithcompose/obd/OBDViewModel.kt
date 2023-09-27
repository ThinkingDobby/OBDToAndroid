package kr.rabbito.obdtoandroidwithcompose.obd

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import kr.rabbito.obdtoandroidwithcompose.data.Repository
import kr.rabbito.obdtoandroidwithcompose.data.entity.Connection
import kr.rabbito.obdtoandroidwithcompose.data.entity.Device
import kr.rabbito.obdtoandroidwithcompose.data.parseRPM
import kr.rabbito.obdtoandroidwithcompose.data.parseSpeed

class OBDViewModel(
    private val repository: Repository
) : ViewModel() {
    var device: Device? = null
    var connection: Connection? = null

    private val _speed: MutableLiveData<Int?> = MutableLiveData()
    val speed: LiveData<Int?> = _speed

    private val _rpm: MutableLiveData<Int?> = MutableLiveData()
    val rpm: LiveData<Int?> = _rpm

    fun loadDevice(address: String, uuid: String) {
        repository.getDevice(address, uuid).let {
            device = it
        }
    }

    suspend fun loadConnection(device: Device?, context: Context) {
        repository.connectToDevice(device, context).let {
            connection = it
        }
    }

    suspend fun startDataLoading(connection: Connection?) {
        while (true) {
            delay(200)
            postValue(repository.getResponse(connection, OBD_SPEED))
            delay(200)
            postValue(repository.getResponse(connection, OBD_RPM))
        }
    }

    private fun postValue(response: Array<Int?>?) {
        if (response == null) return

        when (response[0]) {
            0 -> _speed.postValue(response[1])
            1 -> _rpm.postValue(response[1])
        }
    }
}

class OBDViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OBDViewModel::class.java)) {
            return OBDViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class :: ${modelClass::class.java.simpleName}")
    }
}