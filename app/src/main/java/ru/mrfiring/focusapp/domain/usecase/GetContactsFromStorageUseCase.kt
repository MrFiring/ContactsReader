package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.repository.ContactsRepository

class GetContactsFromStorageUseCase(
    private val repository: ContactsRepository
) {
    operator fun invoke(useStorage: UseStorage) = repository.getContactsFromStorage(useStorage)
}