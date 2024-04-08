package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.RunSetting
import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.StepSetting
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toHighUByte
import com.qualflow.verifyclean.core.utils.toLowUByte

@OptIn(ExperimentalUnsignedTypes::class)
data object StepSettingCommand :
    Command<StepSetting, Spare>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.writeStepSettingBit

    /**
     * @since Based on Verx-Lite Auto
     *
     * Step settings to BLE Command
     * e.x : 82 02 02 3d 00 00 00 1f 00 03 08 03 00 00 00 ba 0b 02 00 00 00 00
     *
     * Bytes -> variable -> value (Type)
     * 82 -> Step setting command bit
     * 02 -> Step Index
     * 02 -> Step number
     * 3d -> Step Type
     * 00 00 -> Liquid Time out 1 -> (UInt16)
     * 00 -> Liquid Time Next Step
     * 1f 00 -> Liquid Volume Bytes
     * 03 -> Liquid volume next step
     * 08 -> input button
     * 03 -> input next step
     * 00 00 00 -> Spare
     * ba 0b -> Max Conductivity -> (UInt16)
     * 02 00 -> Min Conductivity -> (UInt16)
     * 00 00 00 -> Spare
     **/
    override fun toCommand(setting: StepSetting): UByteArray {
        setting.run {
            return ubyteArrayOf(
                commandBit,
                stepNumber.toUByte(),
                stepNumber.toUByte(),
                stepType.coerceAtLeast(0).toUByte(),
                liquidTimeout1.toLowUByte(),
                liquidTimeout1.toHighUByte(),
                liquidTimeNextStep.coerceAtLeast(0).toUByte(),
                liquidVol.toLowUByte(),
                liquidVol.toHighUByte(),
                liquidVolNextStep.coerceAtLeast(0).toUByte(),
                inputButton.coerceAtLeast(0).toUByte(),
                inputNextStep.coerceAtLeast(0).toUByte(),
                BLEConstants.spareBit,
                BLEConstants.spareBit,
                BLEConstants.spareBit,
                maxConductivity.toLowUByte(),
                maxConductivity.toHighUByte(),
                minConductivity.toLowUByte(),
                minConductivity.toHighUByte(),
                BLEConstants.spareBit,
                BLEConstants.spareBit,
                BLEConstants.spareBit
            )
        }
    }

    override fun fromCommand(command: UByteArray): Spare {
        command.run {
            return Spare
        }
    }
}
