package com.qualflow.verifyclean.core.utils

import java.util.UUID

object BLEConstants {

    object Service {

        const val CLEANING_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e"

        /* Device info service */
        const val DEVICE_INFO_SERVICE = "0000180a-0000-1000-8000-00805f9b34fb"
    }

    object Characteristic {

        const val CLEANING_SERVICE_WRITE = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
        const val CLEANING_SERVICE_READ = "6e400003-b5a3-f393-e0a9-e50e24dcca9e"
        const val CLEANING_SERVICE_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"

        /* Manufacturer Name Read Only Characteristic */
        const val MANUFACTURER_NAME = "00002a29-0000-1000-8000-00805f9b34fb"

        /* Model Number Read Only Characteristic */
        const val MODEL_NUMBER = "00002a24-0000-1000-8000-00805f9b34fb"

        /* Serial Number Read Only Characteristic */
        const val SERIAL_NUMBER = "00002a25-0000-1000-8000-00805f9b34fb"

        /* Hardware Revision Read Only Characteristic */
        const val HARDWARE_REVISION = "00002a27-0000-1000-8000-00805f9b34fb"

        /* Firmware Revision Read Only Characteristic */
        const val FIRMWARE_REVISION = "00002a26-0000-1000-8000-00805f9b34fb"

        /* Software Revision Read Only Characteristic */
        const val SOFTWARE_REVISION = "00002a28-0000-1000-8000-00805f9b34fb"
    }

    object Descriptor {
        const val CLEANING_SERVICE_CONFIG_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb"
    }

    const val MTU = 20

    const val SOF = '#' //Start of Frame

    const val EOF = '!' //End of Frame

    const val ESC = '~'

    const val BYTE_STUFF_1 = '1'
    const val BYTE_STUFF_2 = '2'
    const val BYTE_STUFF_3 = '3'

    const val writeRunSettingBit: UByte = 0x81u
    const val writeStepSettingBit: UByte = 0x82u

    const val writeTimeBit: UByte = 0x87u
    const val readTimeBit: UByte = 0x07u

    const val readRunSettingBit: UByte = 0x01u
    const val readStepSettingBit: UByte = 0x02u
    const val readProcessStatusBit: UByte = 0x03u
    const val readRunMeasurementBit: UByte = 0x04u
    const val readStepMeasurementBit: UByte = 0x05u

    const val spareBit: UByte = 0x00u

    const val errorMeasurementBit: UByte = 0x06u

    const val cancelBit: UByte = 0x00u
}