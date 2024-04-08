package com.qualflow.verifyclean.core.ble.domain.bleparsable

@OptIn(ExperimentalUnsignedTypes::class)

/**
 * An abstract base class for all settings-related classes.
 * @param T The type of setting object that should converted into command
 * @param R The type of setting object that should converted from command
 */
abstract class Command<T, R>(val uuid: String) {

    /**
     * command bit for the setting
     * This property should hold the specific command bit or identifier associated with the setting.
     */
    abstract val commandBit: UByte

    /**
     * converts the settings into command
     *
     * e.x [RunSetting] into the command
     */
    abstract fun toCommand(setting: T): UByteArray

    /**
     * converts response received from BLE into its respective setting class
     *
     * e.x command into [RunSetting]
     */
    abstract fun fromCommand(command: UByteArray): R
}