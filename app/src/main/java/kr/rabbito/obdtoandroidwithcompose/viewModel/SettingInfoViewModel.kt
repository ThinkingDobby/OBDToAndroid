package kr.rabbito.obdtoandroidwithcompose.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.first
import kr.rabbito.obdtoandroidwithcompose.data.SettingInfoRepository

class SettingInfoViewModel(
    private val repository: SettingInfoRepository
) : ViewModel() {
    private val _macAddress: MutableLiveData<String?> = MutableLiveData()
    val macAddress: LiveData<String?> = _macAddress

    suspend fun loadMacAddress() {
        _macAddress.postValue(repository.getMacAddress().first())
    }

    suspend fun saveAndSetMacAddress(macAddress: String) {
        repository.setMacAddress(macAddress)
        _macAddress.postValue(macAddress)
    }

    suspend fun clearMacAddress() {
        repository.clearMacAddress()
    }
}

class SettingInfoViewModelFactory(private val repository: SettingInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingInfoViewModel::class.java)) {
            return SettingInfoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class :: ${modelClass::class.java.simpleName}")
    }
}