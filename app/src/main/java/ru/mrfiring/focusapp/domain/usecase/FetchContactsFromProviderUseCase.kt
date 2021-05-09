package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Completable
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import javax.inject.Inject

class FetchContactsFromProviderUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(useStorage: UseStorage): Completable =
        repository.fetchContactsFromProvider(useStorage)
}