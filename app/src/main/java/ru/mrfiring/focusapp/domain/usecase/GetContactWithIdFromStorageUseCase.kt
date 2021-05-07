package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactWithIdFromStorageUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(
        id: Int,
        useStorage: UseStorage
    ) = repository.getContactWithIdFromStorage(id, useStorage)
}