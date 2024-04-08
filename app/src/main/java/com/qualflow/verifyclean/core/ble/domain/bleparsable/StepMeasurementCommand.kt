package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.RequestIndex
import com.qualflow.verifyclean.core.domain.model.StepMeasurement
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toUInt16

@OptIn(ExperimentalUnsignedTypes::class)
data object StepMeasurementCommand :
    Command<RequestIndex, StepMeasurement>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.readStepMeasurementBit

    override fun toCommand(setting: RequestIndex): UByteArray {
        return ubyteArrayOf(
            commandBit,
            setting.index.toUByte()
        )
    }

    /**
     * Write BLE Command for Step Measurement
     * e.x : 05 01 01 11 00 18 00 50 52 52 00 00 00 00 00 00 dc 05 dc 05 dc 05
     *
     * Bytes -> variable -> value (Type)
     * 05 -> step measurement command bit
     * 01 -> step number
     * 01 -> step number
     * 11 00 -> Liquid Time
     * 18 00 -> Liquid Vol Total
     * 50  -> Flow Rate Avg
     * 52 -> Max Liquid Flow Rate
     * 52 -> Min Liquid Flow Rate
     * 00 00 -> Conductivity Avg
     * 00 00 -> Max Conductivity
     * 00 00 -> Min Conductivity
     * dc 05 -> Temperature Avg
     * dc 05 -> Max Temp
     * dc 05 -> Min Temp
     **/
    override fun fromCommand(command: UByteArray): StepMeasurement {
        command.run {
            return StepMeasurement(
                stepNumber = this[2].toInt(),
                liquidTime = this.toUInt16(3).toInt(),
                liquidVolTotal = (this.toUInt16(5) / 10u).toFloat(),
                liquidFlowRateAvg = ((this[7]) / 10u).toFloat(),
                maxLiquidFlowRate = ((this[8]) / 10u).toFloat(),
                minLiquidFlowRate = ((this[9]) / 10u).toFloat(),
                conductivityAvg = ((this.toUInt16(10)) / 100u).toFloat(),
                maxConductivity = ((this.toUInt16(12)) / 100u).toFloat(),
                minConductivity = ((this.toUInt16(14)) / 100u).toFloat(),
                tempAvg = ((this.toUInt16(16)) / 10u).toFloat(),
                maxTemp = ((this.toUInt16(18)) / 10u).toFloat(),
                minTemp = ((this.toUInt16(20)) / 10u).toFloat()
            )
        }
    }
}
