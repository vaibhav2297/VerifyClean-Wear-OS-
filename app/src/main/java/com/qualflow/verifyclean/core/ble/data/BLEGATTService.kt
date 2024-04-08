package com.qualflow.verifyclean.core.ble.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Build
import android.util.Log
import com.qualflow.verifyclean.core.ble.domain.model.ConnectionState
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.ScannedDevice
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseDescriptor
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseRead
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseService
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseWrite
import com.qualflow.verifyclean.core.domain.model.Setting
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toHexString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BLEGATTService @Inject constructor(
    private val context: Context,
    private val parseService: ParseService,
    private val parseRead: ParseRead,
    private val parseWrite: ParseWrite,
    private val parseDescriptor: ParseDescriptor,
    private val bluetoothAdapter: BluetoothAdapter
) : IBLEGATTService {

    private var btGatt: BluetoothGatt? = null

    /**
     * A Flow that provides a device connection state.
     * @return [ConnectionState]
     */
    private val _connectionState: MutableStateFlow<ConnectionState> by lazy {
        MutableStateFlow(ConnectionState.DISCONNECTED)
    }
    override val connectionState = _connectionState.asStateFlow()

    /**
     * A Flow that provides a services associated with the connected device
     * @return [DeviceService]
     */
    private val _deviceServices: MutableStateFlow<List<DeviceService>> by lazy {
        MutableStateFlow(emptyList())
    }
    override val deviceServices = _deviceServices.asStateFlow()

    /**
     * A Flow that provides a connected device
     * @return [ScannedDevice]
     */
    private val _connectedDevice: MutableStateFlow<ScannedDevice?> by lazy {
        MutableStateFlow(null)
    }
    override val connectedDevice = _connectedDevice.asStateFlow()

    /**
     * A Flow that provides a setting received from BLE
     * @return [Setting]
     */
    private val _setting: MutableStateFlow<Setting?> by lazy {
        MutableStateFlow(null)
    }
    override val setting = _setting.asStateFlow()

    private fun isBluetoothEnabled() = bluetoothAdapter.isEnabled

    override fun connectToDevice(scannedDevice: ScannedDevice) {
        if (isBluetoothEnabled()) {
            try {
                _connectedDevice.value = scannedDevice
                _connectionState.value = ConnectionState.CONNECTING
                val device = bluetoothAdapter.getRemoteDevice(scannedDevice.address)
                device.connectGatt(context, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
            } catch (e: Exception) {
                _connectedDevice.value = null
                _connectionState.value = ConnectionState.DISCONNECTED
                Log.d(TAG, "connectToDevice :: exception : ${e.message}")
            }
        } else {
            Log.e(TAG, "connectToDevice :: Bluetooth is not enabled")
            //Error Handling
        }
    }

    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)

            btGatt = gatt
            Log.d(
                BLEScanService.TAG,
                "onConnectionStateChange :: status : $status : newState : $newState"
            )

            when (newState) {
                BluetoothProfile.STATE_CONNECTING -> _connectionState.value =
                    ConnectionState.CONNECTING

                BluetoothProfile.STATE_CONNECTED -> {
                    _connectionState.value = ConnectionState.CONNECTED
                    btGatt?.discoverServices()
                }

                BluetoothProfile.STATE_DISCONNECTING -> _connectionState.value =
                    ConnectionState.DISCONNECTING

                BluetoothProfile.STATE_DISCONNECTED -> {
                    _connectionState.value = ConnectionState.DISCONNECTED
                    _connectedDevice.value = null
                }

                else -> _connectionState.value = ConnectionState.DISCONNECTED
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            Log.d(TAG, "onServicesDiscovered :: status : $status")

            _deviceServices.value = emptyList()
            gatt?.let {
                _deviceServices.value = parseService(it, status)
            }

            CoroutineScope(Dispatchers.IO).launch {
                enableNotificationsAndIndications()
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            Log.d(
                TAG,
                "onCharacteristicChanged :: UUID : ${characteristic.uuid} : value : ${value.toHexString()}"
            )
            _setting.value = parseWrite(characteristic)
        }

        @Suppress("DEPRECATION")
        @Deprecated("Deprecated in Java")
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            Log.d(TAG, "onCharacteristicChanged :: UUID : ${characteristic?.uuid} : value : ${characteristic?.value?.toHexString()}")
            characteristic?.let { char ->
                _setting.value = parseWrite(char)
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, value, status)
            Log.d(
                TAG,
                "onCharacteristicRead :: UUID : ${characteristic.uuid} : value : ${value.decodeToString()}"
            )
            _deviceServices.value = parseRead(_deviceServices.value, characteristic, value, status)
        }

        @Suppress("DEPRECATION")
        @Deprecated("Deprecated in Java")
        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Log.d(
                TAG,
                "onCharacteristicRead :: UUID : ${characteristic?.uuid} : value : ${characteristic?.value?.decodeToString()}"
            )
            characteristic?.let { char ->
                _deviceServices.value = parseRead(_deviceServices.value, char, status)
            }
        }

        @Suppress("DEPRECATION")
        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Log.d(
                TAG,
                "onCharacteristicWrite :: UUID : ${characteristic?.uuid} : value : ${characteristic?.value?.toHexString()}"
            )
            //uncomment
            /*characteristic?.let {
                _setting.value = parseWrite(it, status)
            }*/
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            Log.d(TAG, "onDescriptorWrite :: UUID : ${descriptor?.uuid}")
        }

        override fun onDescriptorRead(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int,
            value: ByteArray
        ) {
            super.onDescriptorRead(gatt, descriptor, status, value)
            Log.d(TAG, "onDescriptorWrite :: UUID : ${descriptor.uuid} : value : ${value.toHexString()}")
            //uncomment
            /*_deviceServices.value = parseDescriptor(_deviceServices.value, descriptor, status)*/
        }

        @Suppress("DEPRECATION")
        @Deprecated("Deprecated in Java")
        override fun onDescriptorRead(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorRead(gatt, descriptor, status)
            Log.d(TAG, "onDescriptorWrite :: UUID : ${descriptor?.uuid} : value : ${descriptor?.value?.toHexString()}")
            //uncomment
            /*descriptor?.let {
                _deviceServices.value = parseDescriptor(_deviceServices.value, descriptor, status)
            }*/
        }
    }

    /**
     * In order to subscribe to notifications on a given characteristic, you must first set the Notifications Enabled bit
     * in its Client Characteristic Configuration Descriptor.
     */
    @Suppress("DEPRECATION")
    suspend fun enableNotificationsAndIndications() {
        btGatt?.services?.forEach { gattSvcForNotify ->
            gattSvcForNotify.characteristics?.forEach { svcChar ->

                svcChar.descriptors.find { desc ->
                    desc.uuid.toString() == BLEConstants.Descriptor.CLEANING_SERVICE_CONFIG_DESCRIPTOR
                }?.also { configDesc ->
                    val notifyRegistered = btGatt?.setCharacteristicNotification(svcChar, true)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (svcChar.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                            btGatt?.writeDescriptor(configDesc, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
                        }

                        if (svcChar.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE > 0) {
                            btGatt?.writeDescriptor(configDesc, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)
                        }
                    } else {

                        if (svcChar.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                            configDesc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
                            btGatt?.writeDescriptor(configDesc)
                        }

                        if (svcChar.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE > 0) {
                            configDesc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)
                            btGatt?.writeDescriptor(configDesc)
                        }
                    }

                    // give gatt a little breathing room for writes
                    delay(300L)
                }
            }
        }
    }

    override fun readCharacteristics(uuid: String) {
        btGatt?.services?.flatMap { it.characteristics }?.find { svcChar ->
            svcChar.uuid.toString() == uuid
        }?.also { foundChar ->
            Log.d(TAG, "readCharacteristics :: Found Char : ${foundChar.uuid}")
            btGatt?.readCharacteristic(foundChar)
        }
    }

    @Suppress("DEPRECATION")
    override fun writeBytes(uuid: String, bytes: ByteArray) {
        try {
            btGatt?.services?.flatMap { it.characteristics }?.find { svcChar ->
                svcChar.uuid.toString() == uuid
            }?.also { foundChar ->
                Log.d(TAG, "writeBytes :: ${bytes.toHexString()}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    btGatt?.writeCharacteristic(
                        foundChar,
                        bytes,
                        BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                    )
                } else {
                    foundChar.value = bytes
                    btGatt?.writeCharacteristic(foundChar)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "writeBytes :: exception : ${e.message}")
            e.printStackTrace()
        }
    }

    override fun close() {
        _connectionState.value = ConnectionState.DISCONNECTING
        try {
            btGatt?.let { gatt ->
                gatt.disconnect()
                gatt.close()
                btGatt = null
                cleanUp()
            }
            _connectionState.value = ConnectionState.DISCONNECTED
        } catch (e: Exception) {
            Log.e(TAG, "close :: exception : ${e.message}")
        } finally {
            btGatt = null
        }
    }

    private fun cleanUp() {
        _deviceServices.value = emptyList()
        _setting.value = null
        _connectedDevice.value = null
    }

    companion object {
        const val TAG = "BLEGATTService"
    }

}