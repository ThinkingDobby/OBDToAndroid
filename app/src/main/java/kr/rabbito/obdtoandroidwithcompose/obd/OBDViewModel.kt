package kr.rabbito.obdtoandroidwithcompose.obd

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private val _maf: MutableLiveData<Int?> = MutableLiveData()
    val maf: LiveData<Int?> = _maf

    private val _throttlePos: MutableLiveData<Int?> = MutableLiveData()
    val throttlePos: LiveData<Int?> = _throttlePos

    private val _engineLoad: MutableLiveData<Int?> = MutableLiveData()
    val engineLoad: LiveData<Int?> = _engineLoad

    private val _coolantTemp: MutableLiveData<Int?> = MutableLiveData()
    val coolantTemp: LiveData<Int?> = _coolantTemp

    private val _fuel: MutableLiveData<Int?> = MutableLiveData()
    val fuel: LiveData<Int?> = _fuel

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

    suspend fun startDataLoading(connection: Connection?, lifecycleOwner: LifecycleOwner) {

        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(30)
                postValue(repository.getResponse(connection, OBD_SPEED))
                delay(30)
                postValue(repository.getResponse(connection, OBD_RPM))
                delay(30)
                postValue(repository.getResponse(connection, OBD_MAF))
                delay(30)
                postValue(repository.getResponse(connection, OBD_THROTTLE_POS))
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                // 30초마다 30ms 간격으로 확인하도록 수정 필요
                postValue(repository.getResponse(connection, OBD_COOLANT_TEMP))
                delay(30000)
                postValue(repository.getResponse(connection, OBD_ENGINE_LOAD))
                delay(30000)
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                // 5분마다 30ms 간격으로 확인하도록 수정 필요
                postValue(repository.getResponse(connection, OBD_FUEL))
                delay(300000)
            }
        }
    }

    private fun postValue(response: Array<Int?>?) {
        if (response == null) return

        Log.d("check responseCode", response[0].toString() + " " + response[1].toString())
        when (response[0]) {
            0 -> _rpm.postValue(response[1])
            1 -> _speed.postValue(response[1])
            2 -> _maf.postValue(response[1])
            3 -> _throttlePos.postValue(response[1])
            4 -> _engineLoad.postValue(response[1])
            5 -> _coolantTemp.postValue(response[1]?.minus(60))
            6 -> _fuel.postValue(response[1])
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