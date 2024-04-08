package com.qualflow.verifyclean.core.ble.data.mock

@ExperimentalUnsignedTypes
object MockCommand {

    /**
     * Write Time
     * 23 87 00 75 96 0e 66 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 21
     */
    val writeTime = byteArrayOf(35, -121, 0, 117, -106, 14, 102, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33)

    /**
     * Read Time
     * [35, 7, 0, 33]
     * 23 07 00 21
     */
    val readTime = byteArrayOf(35, 7, 0, 33)

    /**
     * Run Setting
     * 23 81 00 09 0f 4b 00 05 05 03 14 5e 01 01 00 00 00 00 05 b4 0f 00 00 21
     */
    val runSetting = byteArrayOf(35, -127, 0, 9, 15, 75, 0, 5, 5, 3, 20, 94, 1, 1, 0, 0, 0, 0, 5, -76, 15, 0, 0, 33)

    /**
     * Step 1 Setting
     * 23 82 01 01 3c b4 00 02 1f 00 02 00 00 00 00 00 b9 0b 01 00 00 00 00 21
     */
    private val step1Setting = byteArrayOf(35, -126, 1, 1, 60, 15, 0, 2, 31, 0, 2, 0, 0, 0, 0, 0, -63, 11, 1, 0, 0, 0, 0, 33)

    /**
     * Step 2 Setting
     * 23 82 02 02 3d 00 00 00 20 00 03 08 03 00 00 00 ba 0b 02 00 00 00 00 21
     */
    private val step2Setting = byteArrayOf(35, -126, 2, 2, 61, 15, 0, 0, 32, 0, 3, 8, 3, 0, 0, 0, -70, 11, 2, 0, 0, 0, 0, 33)

    /**
     * Step 3 Setting
     * 23 82 03 03 3e 1e 00 04 00 00 00 00 00 00 00 00 bb 0b 03 00 00 00 00 21
     */
    private val step3Setting = byteArrayOf(35, -126, 3, 3, 62, 15, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, -69, 11, 3, 0, 0, 0, 0, 33)

    /**
     * Step 4 Setting
     * 23 82 04 04 3f 00 00 00 b6 00 05 08 05 00 00 00 bc 0b 04 00 00 00 00 21
     */
    private val step4Setting = byteArrayOf(35, -126, 4, 4, 63, 0, 0, 0, -74, 0, 5, 8, 5, 0, 0, 0, -68, 11, 4, 0, 0, 0, 0, 33)

    /**
     * Step 5 Setting
     * 23 82 05 05 3e 1e 00 06 00 00 00 00 00 00 00 00 bd 0b 05 00 00 00 00 21
     */
    private val step5Setting = byteArrayOf(35, -126, 5, 5, 62, 15, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, -67, 11, 5, 0, 0, 0, 0, 33)

    /**
     * Step 6 Setting
     * 23 82 06 06 40 b4 00 07 19 00 07 00 00 00 00 00 be 0b 06 00 00 00 00 21
     */
    private val step6Setting = byteArrayOf(35, -126, 6, 6, 64, 0, 0, 7, 25, 0, 7, 0, 0, 0, 0, 0, -66, 11, 6, 0, 0, 0, 0, 33)

    /**
     * Step 7 Setting
     * 23 82 07 07 41 1e 00 08 00 00 00 00 00 00 00 00 bf 0b 07 00 00 00 00 21
     */
    private val step7Setting = byteArrayOf(35, -126, 7, 7, 65, 15, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, -65, 11, 7, 0, 0, 0, 0, 33)

    /**
     * Step 8 Setting
     * 23 82 08 08 40 78 00 09 11 00 09 00 00 00 00 00 c0 0b 08 00 00 00 00 21
     */
    private val step8Setting = byteArrayOf(35, -126, 8, 8, 64, 0, 0, 9, 17, 0, 9, 0, 0, 0, 0, 0, -64, 11, 8, 0, 0, 0, 0, 33)

    /**
     * Step 9 Setting
     * 23 82 09 09 41 1e 00 0a 00 00 00 00 00 00 00 00 c1 0b 09 00 00 00 00 21
     */
    private val step9Setting = byteArrayOf(35, -126, 9, 9, 65, 15, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, -63, 11, 9, 0, 0, 0, 0, 33)

    /**
     * Step 10 Setting
     * 23 82 0a 0a 42 1e 00 0b 00 00 00 07 0b 00 00 00 c2 0b 0a 00 00 00 00 21
     */
    private val step10Setting = byteArrayOf(35, -126, 10, 10, 66, 15, 0, 11, 0, 0, 0, 7, 11, 0, 0, 0, -62, 11, 10, 0, 0, 0, 0, 33)

    /**
     * Step 11 Setting
     * 23 82 0b 0b 43 00 00 00 48 00 0c 08 0c 00 00 00 c3 0b 0b 00 00 00 00 21
     */
    private val step11Setting = byteArrayOf(35, -126, 11, 11, 67, 0, 0, 0, 72, 0, 12, 8, 12, 0, 0, 0, -61, 11, 11, 0, 0, 0, 0, 33)

    /**
     * Step 12 Setting
     * 23 82 0c 0c 44 1e 00 0d 00 00 00 07 0d 00 00 00 c4 0b 0c 00 00 00 00 21
     */
    private val step12Setting = byteArrayOf(35, -126, 12, 12, 68, 15, 0, 13, 0, 0, 0, 7, 13, 0, 0, 0, -60, 11, 12, 0, 0, 0, 0, 33)

    /**
     * Step 13 Setting
     * 23 82 0d 0d 45 1e 00 0e 00 00 00 07 0e 00 00 00 c5 0b 0d 00 00 00 00 21
     */
    private val step13Setting = byteArrayOf(35, -126, 13, 13, 69, 0, 0, 14, 0, 0, 0, 7, 14, 0, 0, 0, -59, 11, 13, 0, 0, 0, 0, 33)

    /**
     * Step 14 Setting
     * 23 82 0e 0e 46 b4 00 0f 33 00 0f 00 00 00 00 00 c6 0b 0e 00 00 00 00 21
     */
    private val step14Setting = byteArrayOf(35, -126, 14, 14, 70, 0, 0, 15, 51, 0, 15, 0, 0, 0, 0, 0, -58, 11, 14, 0, 0, 0, 0, 33)

    /**
     * Step 15 Setting
     * 23 82 0f 0f 43 00 00 00 34 00 00 08 00 00 00 00 c7 0b 0f 00 00 00 00 21
     */
    private val step15Setting = byteArrayOf(35, -126, 15, 15, 67, 15, 0, 0, 52, 0, 0, 8, 0, 0, 0, 0, -57, 11, 15, 0, 0, 0, 0, 33)

    val stepSettings = listOf(
        step1Setting,
        step2Setting,
        step3Setting,
        step4Setting,
        step5Setting,
        step6Setting,
        step7Setting,
        step8Setting,
        step9Setting,
        step10Setting,
        step11Setting,
        step12Setting,
        step13Setting,
        step14Setting,
        step15Setting
    )

    /**
     * Status Measurement
     * 23 03 00 21
     */
    val statusMeasurement = byteArrayOf(35, 3, 0, 33)

    /**
     * Run Measurement
     * 23 04 00 21
     */
    val runMeasurement = byteArrayOf(35, 4, 0, 33)

    /**
     * Step Measurement
     * 23 05 {step index} 21
     */
    fun stepMeasurement(step: Int): ByteArray {
        return byteArrayOf(35, 5, step.toByte(), 33)
    }

    /**
     * Cancel command
     * 23 00 00 21
     */
    val cancel = byteArrayOf(35, 0, 0, 33)

}