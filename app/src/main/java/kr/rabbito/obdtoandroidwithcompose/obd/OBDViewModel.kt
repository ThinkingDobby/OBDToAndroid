package kr.rabbito.obdtoandroidwithcompose.obd

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.rabbito.obdtoandroidwithcompose.data.Repository
import kr.rabbito.obdtoandroidwithcompose.data.entity.Connection
import kr.rabbito.obdtoandroidwithcompose.data.entity.Device

class OBDViewModel(
    private val repository: Repository
) : ViewModel() {
    var device: Device? = null
    var connection: Connection? = null

    private val _speed: MutableLiveData<Int?> = MutableLiveData()
    val speed: LiveData<Int?> = _speed

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

    suspend fun startSpeedLoading(connection: Connection?) {
        while (true) {
            repository.getSpeed(connection).let {
                _speed.postValue(it)
            }
//            Log.d("check delay", "checking...")
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