package ru.mrfiring.focusapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact

interface ContactsRepository {
    fun fetchContactsFromProvider(useStorage: UseStorage): Completable

    fun getContactsFromStorage(useStorage: UseStorage): Flowable<List<DomainContact>>

    fun updateContact(contact: DomainContact, useStorage: UseStorage): Completable

    fun getContactWithIdFromStorage(id: Int, useStorage: UseStorage): Single<DomainContact>

    fun removeContact(contact: DomainContact, useStorage: UseStorage): Completable
}