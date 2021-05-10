package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Completable
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveUseStorageUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(storage: UseStorage): Completable = repository.saveInt(
        preferencesKey = PreferencesKeys.USE_STORAGE,
        storage.value
    )
}