package ru.mrfiring.focusapp.domain.usecase

import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(
        contact: DomainContact,
        useStorage: UseStorage
    ) = repository.updateContact(contact, useStorage)
}