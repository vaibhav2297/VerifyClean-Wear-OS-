package com.qualflow.verifyclean.core.domain.model

/**
 * A data class to request a command with particular index
 *
 * E.x 05 01
 * For Request [StepMeasurement] setting command with [index]. Use [RequestIndex] in such scenario
 */
data class RequestIndex(
    val index: Int
) : Setting()