package com.qualflow.verifyclean.core.ble.data.mock.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.qualflow.verifyclean.core.ble.data.BLEGATTService
import com.qualflow.verifyclean.core.ble.data.BLEScanService
import com.qualflow.verifyclean.core.ble.data.IBLEGATTService
import com.qualflow.verifyclean.core.ble.data.IBLEScanService
import com.qualflow.verifyclean.core.ble.data.mock.MockCleaning
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.repository.BLERepository
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseRead
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseService
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseWrite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockModule {

    @Provides
    @Singleton
    fun bindMockCleaning(
        bleRepository: IBLERepository
    ): MockCleaning =
        MockCleaning(bleRepository)
}