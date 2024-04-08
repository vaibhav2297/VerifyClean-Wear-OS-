package com.qualflow.verifyclean.core.utils

fun getCharacteristicName(uuid: String): String {
    return when(uuid) {
        BLEConstants.Characteristic.MANUFACTURER_NAME -> "Manufacturer name"
        BLEConstants.Characteristic.FIRMWARE_REVISION -> "Firmware revision"
        BLEConstants.Characteristic.HARDWARE_REVISION -> "Hardware revision"
        BLEConstants.Characteristic.MODEL_NUMBER -> "Model number"
        BLEConstants.Characteristic.SERIAL_NUMBER -> "Serial number"
        BLEConstants.Characteristic.SOFTWARE_REVISION -> "Software revision"
        BLEConstants.Characteristic.CLEANING_SERVICE_CONFIG -> "Cleaning config"
        BLEConstants.Characteristic.CLEANING_SERVICE_WRITE -> "Cleaning write"
        BLEConstants.Characteristic.CLEANING_SERVICE_READ -> "Cleaning read"
        else -> "Unknown characteristic"
    }
}

fun getServiceName(uuid: String): String {
    return when(uuid) {
        BLEConstants.Service.CLEANING_SERVICE -> "Cleaning service"
        BLEConstants.Service.DEVICE_INFO_SERVICE -> "Device info service"
        else -> "Unknown service"
    }
}

fun getDescriptorName(uuid: String): String {
    return when(uuid) {
        BLEConstants.Descriptor.CLEANING_SERVICE_CONFIG_DESCRIPTOR -> "Cleaning config descriptor"
        else -> "Unknown descriptor"
    }
}