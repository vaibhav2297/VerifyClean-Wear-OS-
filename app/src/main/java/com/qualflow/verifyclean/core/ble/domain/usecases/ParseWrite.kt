package com.qualflow.verifyclean.core.ble.domain.usecases

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import com.qualflow.verifyclean.core.ble.domain.bleparsable.Command
import com.qualflow.verifyclean.core.ble.domain.bleparsable.ErrorMeasurementCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.ReadTimeCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.RunMeasurementCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.RunSettingCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.StatusMeasurementCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.StepMeasurementCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.StepSettingCommand
import com.qualflow.verifyclean.core.ble.domain.bleparsable.WriteTimeCommand
import com.qualflow.verifyclean.core.ble.domain.model.DeviceService
import com.qualflow.verifyclean.core.ble.domain.model.updateBytes
import com.qualflow.verifyclean.core.domain.model.Setting
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.intoUByte
import com.qualflow.verifyclean.core.utils.removeBleFrame

@Suppress("DEPRECATION")
@OptIn(ExperimentalUnsignedTypes::class)
class ParseWrite {

    operator fun invoke(
        characteristic: BluetoothGattCharacteristic
    ): Setting? {
        return characteristic.value?.let { value ->
            val removedFrame = value.toUByteArray().removeBleFrame()
            parseCommandAndCreateRequest(removedFrame)
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun parseCommandAndCreateRequest(command: UByteArray): Setting? {
        command.run {
            require(value = command.first() != BLEConstants.SOF.intoUByte()) { "Command Response contains start frame and end frame. Apply UByteArray.removeBleFrame() first " }

            return when (this.first()) {
                BLEConstants.readTimeBit -> {
                    ReadTimeCommand.fromCommand(this)
                }
                BLEConstants.writeTimeBit -> {
                    WriteTimeCommand.fromCommand(this)
                }
                BLEConstants.writeRunSettingBit -> {
                    RunSettingCommand.fromCommand(this)
                }
                BLEConstants.writeStepSettingBit -> {
                    StepSettingCommand.fromCommand(this)
                }
                BLEConstants.readProcessStatusBit -> {
                    StatusMeasurementCommand.fromCommand(this)
                }
                BLEConstants.readRunMeasurementBit -> {
                    RunMeasurementCommand.fromCommand(this)
                }
                BLEConstants.readStepMeasurementBit -> {
                    StepMeasurementCommand.fromCommand(this)
                }
                BLEConstants.errorMeasurementBit -> {
                    ErrorMeasurementCommand.fromCommand(this)
                }
                else -> null
            }
        }
    }
}