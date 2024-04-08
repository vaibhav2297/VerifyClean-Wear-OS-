package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.RunMeasurement
import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.getCleanType
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toUInt16
import com.qualflow.verifyclean.core.utils.toUInt32

@OptIn(ExperimentalUnsignedTypes::class)
data object RunMeasurementCommand :
    Command<Spare, RunMeasurement>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.readRunMeasurementBit

    override fun toCommand(setting: Spare): UByteArray {
        return ubyteArrayOf(
            commandBit,
            BLEConstants.spareBit
        )
    }

    /**
     * Write BLE Command for Run Measurement
     * e.x : 04 00 2d 00 09 0f b2 32 60 65 a5 34 60 65 00 00 00 00 00 00 00 00
     *
     * Bytes -> variable -> value (Type)
     * 04 -> run measurement command bit
     * 00 -> Spare
     * 2d 00 -> run number -> (UInt16)
     * 09 -> Type of Clean
     * 0f -> Number of Steps
     * b2 32 60 65 -> Clean start DateTime -> (UInt32)
     * a5 34 60 65 -> Clean end DateTime -> (UInt32)
     * 00 -> Number of Error -> (UInt32)
     * 00 00 00 00 00 00 -> Spare bytes
     **/
    override fun fromCommand(command: UByteArray): RunMeasurement {
        command.run {
            return RunMeasurement(
               runNumber = this.toUInt16(2).toInt(),
                cleanType = getCleanType(this[4].toInt()),
                numberOfSteps = this[5].toInt(),
                cleanStartTime = this.toUInt32(6).toLong(),
                cleanEndTime = this.toUInt32(10).toLong(),
                numberOfErrors = this[14].toInt()
            )
        }
    }
}
