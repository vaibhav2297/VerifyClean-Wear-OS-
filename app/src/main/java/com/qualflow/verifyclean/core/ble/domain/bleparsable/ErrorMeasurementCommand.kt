package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.ErrorMeasurement
import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.StepError
import com.qualflow.verifyclean.core.domain.model.getCleanError
import com.qualflow.verifyclean.core.utils.BLEConstants

@OptIn(ExperimentalUnsignedTypes::class)
data object ErrorMeasurementCommand :
    Command<Spare, ErrorMeasurement>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.errorMeasurementBit

    override fun toCommand(setting: Spare): UByteArray {
        return ubyteArrayOf(
            commandBit,
            BLEConstants.spareBit
        )
    }

    /**
     * Write BLE Command for Step Errors
     * e.x : 06 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
     *
     * Bytes -> variable -> value (Type)
     * 06 -> Error measurement command bit
     * 00 -> Spare
     * 00 -> Error Number
     * 00 -> Step Number
     * 00 00 00 -> Remaining Spare
     **/
    override fun fromCommand(command: UByteArray): ErrorMeasurement {
        val stepErrors = mutableListOf<StepError>()
        command.run {
            for (i in 2 until this.size step 2) {
                val stepNumber = this[i].toInt()
                val errorCode = this[i + 1].toInt()

                //break the loop if remaining are spares
                if (stepNumber == 0) {
                    break
                }

                stepErrors.add(
                    StepError(
                        stepNumber = stepNumber,
                        cleanError = getCleanError(errorCode)
                    )
                )
            }
            return ErrorMeasurement(
                stepErrors = stepErrors
            )
        }
    }
}
