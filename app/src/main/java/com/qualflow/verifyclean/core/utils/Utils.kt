package com.qualflow.verifyclean.core.utils

const val BRAND_NAME = "Qualflow"

fun retrieveHardwareId(serialNumber: String): String {
    val removedSerial = removeBrandName(serialNumber)
    val indexOfHyphen = removedSerial.indexOf('-')
    return if (indexOfHyphen != -1) {
        removedSerial.substring(startIndex = 0, endIndex = indexOfHyphen)
    } else {
        removedSerial
    }
}

fun removeBrandName(input: String): String {
    return if (input.startsWith(BRAND_NAME, ignoreCase = true)) {
        // Remove the prefix
        input.substring(BRAND_NAME.length).trimStart()
    } else {
        input
    }
}