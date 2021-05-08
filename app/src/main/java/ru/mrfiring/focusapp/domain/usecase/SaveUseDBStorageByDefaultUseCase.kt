package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveUseDBStorageByDefaultUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(value: Boolean) = repository.saveBoolean(
        preferencesKey = PreferencesKeys.USE_DB_STORAGE_BY_DEFAULT,
        value
    )
}