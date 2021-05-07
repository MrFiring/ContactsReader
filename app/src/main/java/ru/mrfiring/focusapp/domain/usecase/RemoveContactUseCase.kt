package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.repository.ContactsRepository

class RemoveContactUseCase(
    private val repository: ContactsRepository
) {
    operator fun invoke(
        contact: DomainContact,
        useStorage: UseStorage
    ) = repository.removeContact(contact, useStorage)
}