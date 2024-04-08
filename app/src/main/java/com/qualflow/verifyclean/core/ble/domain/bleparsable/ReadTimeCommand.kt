package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.Time
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.toUInt32

@OptIn(ExperimentalUnsignedTypes::class)
data object ReadTimeCommand : Command<Spare, Time>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.readTimeBit

    override fun toCommand(setting: Spare): UByteArray {
        return ubyteArrayOf(
            commandBit,
            BLEConstants.spareBit
        )
    }

    /**
     * BLE command to Read Time
     *
     * e.x : 07 00
     *
     * - Bytes -> variable -> value (Type)
     * - 07 -> Write time command bit
     * - 00 -> Spare
     **/
    override fun fromCommand(command: UByteArray): Time {
        return Time(
            time = command.toUInt32(2).toLong()
        )
    }
}
