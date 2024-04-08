package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.StatusMeasurement
import com.qualflow.verifyclean.core.domain.model.getCleanError
import com.qualflow.verifyclean.core.domain.model.getCleanStatus
import com.qualflow.verifyclean.core.domain.model.getCleanType
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toUInt16

@OptIn(ExperimentalUnsignedTypes::class)
data object StatusMeasurementCommand :
    Command<Spare, StatusMeasurement>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.readProcessStatusBit

    override fun toCommand(setting: Spare): UByteArray {
        return ubyteArrayOf(
            commandBit,
            BLEConstants.spareBit
        )
    }

    /**
     * BLE Command for Status Measurement
     *
     * e.x : 03 00 09 1b 00 03 3e 0a 00 01 00 0e 00 52 08 00 00 dc 05 00 00 00
     *
     * Bytes -> variable -> value (Type)
     * 03 -> status measurement command bit
     * 00 -> Spare
     * 09 -> Type of Clean / Process Type
     * 1b 00 -> Process Time -> 27 (UInt16)
     * 03 -> current step number
     * 3e -> current process step type
     * 0a 00 -> current process step time -> 10 (UInt16)
     * 01 -> clean status
     * 00 -> error code
     * 0e 00 -> Liquid Vol Current -> 14 (UInt16)
     * 52 -> Current Flow Rate
     * 08 -> Flow Rate Percentage Target
     * 00 00 -> Current Conductivity Avg -> 0 (UInt16)
     * dc 05 -> Current Temp Avg -> 1500 (UInt16)
     * 00 00 00 -> Spare
     **/
    override fun fromCommand(command: UByteArray): StatusMeasurement {
        val errorCode = command[10].toInt()
        val cleanStatus = getCleanStatus(command[9].toInt(), errorCode)
        val cleanError = getCleanError(errorCode)

        command.run {
            return StatusMeasurement(
               processType = getCleanType(this[2].toInt()),
                processTime = this.toUInt16(3).toLong(),
                currentStepNo = this[5].toInt(),
                currentProcessStepType = this[6].toInt(),
                currentProcessStepTime = this.toUInt16(7).toInt(),
                cleanStatus = cleanStatus,
                errorCode = cleanError,
                liquidVolCurrent = (this.toUInt16(11) / 10u).toFloat(),
                currentFlowRate = (this[13] / 10u).toFloat(),
                flowRatePercentageTarget = this[14].toInt(),
                currentConductivityAvg = (this.toUInt16(15) / 100u).toFloat(),
                currentTempAvg = (this.toUInt16(17) / 10u).toFloat()
            )
        }
    }
}
