package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Completable
import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveFirstLaunchPassedUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(value: Boolean): Completable = repository.saveBoolean(
        preferencesKey = PreferencesKeys.FIRST_LAUNCH_PASSED,
        value
    )
}