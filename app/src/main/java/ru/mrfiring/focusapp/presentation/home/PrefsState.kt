package ru.mrfiring.focusapp.presentation.home

import ru.mrfiring.focusapp.domain.UseStorage

data class PrefsState(
    val isFirstLaunchPassed: Boolean,
    val useStorage: UseStorage
)
