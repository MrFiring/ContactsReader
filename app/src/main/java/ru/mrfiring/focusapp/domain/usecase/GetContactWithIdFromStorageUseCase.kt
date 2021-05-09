package ru.mrfiring.focusapp.domain.usecase

import io.reactivex.Single
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactWithIdFromStorageUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(
        id: Int,
        useStorage: UseStorage
    ): Single<DomainContact> = repository.getContactWithIdFromStorage(id, useStorage)
}