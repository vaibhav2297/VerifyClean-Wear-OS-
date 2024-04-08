package com.qualflow.verifyclean.core.ble.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.qualflow.verifyclean.core.ble.data.BLEGATTService
import com.qualflow.verifyclean.core.ble.data.BLEScanService
import com.qualflow.verifyclean.core.ble.data.IBLEGATTService
import com.qualflow.verifyclean.core.ble.data.IBLEScanService
import com.qualflow.verifyclean.core.ble.data.repository.IBLERepository
import com.qualflow.verifyclean.core.ble.domain.repository.BLERepository
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseDescriptor
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
object BleModule {

    @Provides
    @Singleton
    fun bindBLEScanService(
        bluetoothAdapter: BluetoothAdapter
    ): IBLEScanService =
        BLEScanService(bluetoothAdapter)

    @Provides
    @Singleton
    fun bindBLEGATTService(
        @ApplicationContext context: Context,
        parseService: ParseService,
        parseRead: ParseRead,
        parseWrite: ParseWrite,
        parseDescriptor: ParseDescriptor,
        bluetoothAdapter: BluetoothAdapter
    ): IBLEGATTService =
        BLEGATTService(
            context = context,
            parseService = parseService,
            parseRead = parseRead,
            parseWrite = parseWrite,
            parseDescriptor = parseDescriptor,
            bluetoothAdapter = bluetoothAdapter
        )

    @Provides
    @Singleton
    fun bindBLERepository(
        bleScanService: IBLEScanService,
        bleGattService: IBLEGATTService
    ): IBLERepository =
        BLERepository(bleGattService, bleScanService)

    @Provides
    @Singleton
    fun provideBluetoothManager(
        @ApplicationContext context: Context
    ): BluetoothManager =
        context.getSystemService(BluetoothManager::class.java)

    @Provides
    @Singleton
    fun provideBluetoothAdapter(
        bluetoothManager: BluetoothManager
    ): BluetoothAdapter =
        bluetoothManager.adapter
}