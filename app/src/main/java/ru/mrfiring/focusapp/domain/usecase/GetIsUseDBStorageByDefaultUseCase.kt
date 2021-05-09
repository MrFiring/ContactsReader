package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Single
import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetIsUseDBStorageByDefaultUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Single<Boolean> = repository.getBoolean(
        preferencesKey = PreferencesKeys.USE_DB_STORAGE_BY_DEFAULT,
        defaultValue = true
    )
}