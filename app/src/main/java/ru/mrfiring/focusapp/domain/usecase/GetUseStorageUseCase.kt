package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Single
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetUseStorageUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Single<UseStorage> = repository.getInt(
        preferencesKey = PreferencesKeys.USE_STORAGE,
        defaultValue = UseStorage.DATABASE.value
    ).map { UseStorage.parse(it) }
}