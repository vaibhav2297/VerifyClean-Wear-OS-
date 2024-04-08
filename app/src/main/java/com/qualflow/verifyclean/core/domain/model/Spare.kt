package com.qualflow.verifyclean.core.domain.model

/**
 * A data class to represent the command that returns only spare value
 *
 * E.x 87 00
 * For Write [Time] setting command response only contains spare values. Use [Spare] in such scenario
 */
data object Spare : Setting()