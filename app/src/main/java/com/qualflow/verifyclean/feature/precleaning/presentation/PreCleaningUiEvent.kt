package com.qualflow.verifyclean.feature.precleaning.presentation

sealed interface PreCleaningUiEvent {

    data object OnStartClean : PreCleaningUiEvent

    data object OnCleaningStarted : PreCleaningUiEvent
}