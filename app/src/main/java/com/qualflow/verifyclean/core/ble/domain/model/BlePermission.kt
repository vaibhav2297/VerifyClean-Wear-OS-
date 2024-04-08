package com.qualflow.verifyclean.core.ble.domain.model

enum class BlePermissions(val value: Int) {
    PERMISSION_READ(1),
    PERMISSION_READ_ENCRYPTED(2),
    PERMISSION_READ_ENCRYPTED_MITM(4),
    PERMISSION_WRITE(16),
    PERMISSION_WRITE_ENCRYPTED(32),
    PERMISSION_WRITE_ENCRYPTED_MITM(64),
    PERMISSION_WRITE_SIGNED(128),
    PERMISSION_WRITE_SIGNED_MITM(256);

    companion object {

        fun getAllPermissions(bleValue: Int): List<BlePermissions> {
            val propertyList = mutableListOf<BlePermissions>()

            entries.forEach {
                if (bleValue and it.value > 0)
                    propertyList.add(it)
            }
            return propertyList

        }

        fun List<BlePermissions>.canWritePermissions(): Boolean = this.any(
            listOf(
                BlePermissions.PERMISSION_WRITE, BlePermissions.PERMISSION_WRITE_ENCRYPTED,
                BlePermissions.PERMISSION_WRITE_ENCRYPTED_MITM, BlePermissions.PERMISSION_WRITE_SIGNED,
                BlePermissions.PERMISSION_WRITE_SIGNED_MITM
            )::contains
        )
    }
}