package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.repository.ContactsRepository

class GetContactWithIdFromStorageUseCase(
    private val repository: ContactsRepository
) {
    operator fun invoke(
        id: Int,
        useStorage: UseStorage
    ) = repository.getContactWithIdFromStorage(id, useStorage)
}