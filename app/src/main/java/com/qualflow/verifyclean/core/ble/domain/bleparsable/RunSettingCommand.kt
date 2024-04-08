package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.RunSetting
import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toHighUByte
import com.qualflow.verifyclean.core.utils.toLowUByte

@OptIn(ExperimentalUnsignedTypes::class)
data object RunSettingCommand : Command<RunSetting, Spare>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.writeRunSettingBit

    /**
     * @since Based on Verx-Lite Auto
     *
     * Run settings to BLE Command
     * e.x : 81 00 09 0F 4B 00 05 05 03 14 5E 01 01 00 00 00 00 05 B4 0F 00 00
     *
     * Bytes -> variable -> value (Type)
     * 81 -> Run setting command bit
     * 00 -> Spare
     * 09 -> Type of Clean / Process Type
     * 0F -> Number of Steps -> 15
     * 4B 00 -> Flow Meter Calibration -> 75 (UInt16)
     * 05 -> Flow Rate Averaging Seconds
     * 05 -> Conductivity Averaging Seconds
     * 03 -> Temperature Averaging Seconds
     * 14 -> Error Clear Vol
     * 5E 01 -> Temp Max Water -> 350 (UInt16)
     * 01 00 -> Temp Min Water -> (UInt16)
     * 00 -> Flow Rate Scaling LEDs Target
     * 00 -> Flow Rate Scaling LEDs Max
     * 00 -> Flow Rate Scaling LEDs Min
     * 05 -> Soak On time
     * B4 -> Soak Off time
     * 0F -> FOB Time
     * 00 -> Detergent Error Detect Vol
     * 00 -> Spare
     **/
    override fun toCommand(setting: RunSetting): UByteArray {
        setting.run {
            return ubyteArrayOf(
                commandBit,
                BLEConstants.spareBit,
                cleanProcessType.toUByte(),
                numberOfSteps.toUByte(),
                flowMeterCalibration.toLowUByte(),
                flowMeterCalibration.toHighUByte(),
                flowRateAverage.toUByte(),
                conductivityAverage.toUByte(),
                tempAverage.toUByte(),
                (errorClearVolume * 10).toInt().toUByte(),
                (tempMaxWater * 10).toLowUByte(),
                (tempMaxWater * 10).toHighUByte(),
                (tempMinWater * 10).toLowUByte(),
                (tempMinWater * 10).toHighUByte(),
                BLEConstants.spareBit,
                BLEConstants.spareBit,
                BLEConstants.spareBit,
                soakOnTime.toInt().toUByte(),
                soakOffTime.toInt().toUByte(),
                FOBTime.toInt().toUByte(),
                BLEConstants.spareBit,
                BLEConstants.spareBit,
            )
        }
    }

    override fun fromCommand(command: UByteArray): Spare {
        command.run {
            return Spare
        }
    }
}
