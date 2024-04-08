package com.qualflow.verifyclean.core.ble

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.qualflow.verifyclean.core.ble.data.BLEScanService
import javax.inject.Inject

class BLEBoundService @Inject constructor(
    private val bleScanService: BLEScanService
) : Service() {

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService() : BLEScanService {
            return bleScanService
        }
    }
}