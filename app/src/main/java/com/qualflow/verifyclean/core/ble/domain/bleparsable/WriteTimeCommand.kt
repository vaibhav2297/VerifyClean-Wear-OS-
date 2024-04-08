package com.qualflow.verifyclean.core.ble.domain.bleparsable

import com.qualflow.verifyclean.core.domain.model.Spare
import com.qualflow.verifyclean.core.domain.model.Time
import com.qualflow.verifyclean.core.utils.BLEConstants
import com.qualflow.verifyclean.core.utils.to4UByteArray
import com.qualflow.verifyclean.core.utils.toUInt32

@OptIn(ExperimentalUnsignedTypes::class)
data object WriteTimeCommand : Command<Time, Spare>(BLEConstants.Characteristic.CLEANING_SERVICE_WRITE) {

    override val commandBit: UByte = BLEConstants.writeTimeBit

    /**
     * Write Time to Command
     * e.x : 87 00 81 32 60 65 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
     *
     * Bytes -> variable -> value (Type)
     * 87 -> Write time command bit
     * 00 -> Spare
     * 81 32 60 65 -> Time
     * rest -> Spare
     **/
    override fun toCommand(setting: Time): UByteArray {
        return ubyteArrayOf(
            commandBit,
            BLEConstants.spareBit,
            *setting.time.to4UByteArray(),
            *(List(16) { BLEConstants.spareBit }).toUByteArray()
        )
    }

    override fun fromCommand(command: UByteArray): Spare {
        command.run {
            return Spare
        }
    }
}
