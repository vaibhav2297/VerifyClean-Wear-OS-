package com.qualflow.verifyclean.feature.cleaning.presentation

sealed interface CleaningUiEvent {

    data object OnCancelClean : CleaningUiEvent

    data object OnCleanMeasurement : CleaningUiEvent
}