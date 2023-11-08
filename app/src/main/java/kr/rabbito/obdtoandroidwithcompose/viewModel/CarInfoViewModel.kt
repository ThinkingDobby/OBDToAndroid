package kr.rabbito.obdtoandroidwithcompose.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.rabbito.obdtoandroidwithcompose.data.CarInfoRepository
import kr.rabbito.obdtoandroidwithcompose.data.entity.Connection
import kr.rabbito.obdtoandroidwithcompose.data.entity.Device

class CarInfoViewModel(
    private val repository: CarInfoRepository
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

    private val _fuelRate: MutableLiveData<Int?> = MutableLiveData()
    val fuelRate: LiveData<Int?> = _fuelRate

    private val checkState = MutableList(8){false}

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
        val speedCode = repository.getInfoCode("speed")
        val rpmCode = repository.getInfoCode("rpm")
        val mafCode = repository.getInfoCode("maf")
        val throttlePosCode = repository.getInfoCode("throttlePos")
        val engineLoadCode = repository.getInfoCode("engineLoad")
        val coolantTempCode = repository.getInfoCode("coolantTemp")
        val fuelCode = repository.getInfoCode("fuel")
        val fuelRateCode = repository.getInfoCode("fuelRate")

        // 실시간
        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(30)
                postValue(repository.getResponse(connection, speedCode))
                delay(30)
                postValue(repository.getResponse(connection, rpmCode))
                delay(30)
                postValue(repository.getResponse(connection, mafCode))
                delay(30)
                postValue(repository.getResponse(connection, throttlePosCode))
            }
        }

        // 30초 간격
        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                checkState[4] = false
                checkState[5] = false
                while (!checkState[4] || !checkState[5]) {  // 정보 얻을 때까지는 50ms 간격으로 반복
                    postValue(repository.getResponse(connection, engineLoadCode))
                    delay(50)
                    postValue(repository.getResponse(connection, coolantTempCode))
                    delay(50)
                }
                delay(30000)    // 정보 얻으면 30초 대기
            }
        }

        // 70초 간격
        lifecycleOwner.lifecycleScope.launch {
            while (true) {
                checkState[6] = false
                checkState[7] = false
                while (!checkState[6] || !checkState[7]) {
                    postValue(repository.getResponse(connection, fuelCode))
                    delay(70)
                    postValue(repository.getResponse(connection, fuelRateCode))
                    delay(70)
                }
                delay(70000)
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
            7 -> _fuelRate.postValue(response[1])
        }

        if (!checkState[response[0]!!]) checkState[response[0]!!] = true
    }
}

class CarInfoViewModelFactory(private val repository: CarInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarInfoViewModel::class.java)) {
            return CarInfoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class :: ${modelClass::class.java.simpleName}")
    }
}