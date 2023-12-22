package kr.rabbito.obdtoandroidwithcompose

import android.app.Application
import kr.rabbito.obdtoandroidwithcompose.data.DataStore

class OBDToAndroidApplication : Application() {
    private lateinit var dataStore: DataStore

    companion object {
        private lateinit var obdToAndroidApplication: OBDToAndroidApplication
        fun getInstance(): OBDToAndroidApplication = obdToAndroidApplication
    }

    override fun onCreate() {
        super.onCreate()

        obdToAndroidApplication = this
        dataStore = DataStore(this)
    }

    fun getDataStore(): DataStore = dataStore
}