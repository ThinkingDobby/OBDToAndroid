package kr.rabbito.obdtoandroidwithcompose.obd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.rabbito.obdtoandroidwithcompose.data.Repository

class OBDViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _speed: MutableLiveData<Int?> = MutableLiveData()
    val speed: LiveData<Int?> = _speed


}

class OBDViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OBDViewModel::class.java)) {
            return OBDViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class :: ${modelClass::class.java.simpleName}")
    }
}