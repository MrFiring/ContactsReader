package ru.mrfiring.focusapp.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.mrfiring.focusapp.data.database.ContactsDao
import ru.mrfiring.focusapp.data.file.LocalContactsSource
import ru.mrfiring.focusapp.data.mapper.*
import ru.mrfiring.focusapp.data.provider.ContactsProvider
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsDao: ContactsDao,
    private val localContactsSource: LocalContactsSource,
    private val contactsProvider: ContactsProvider
) : ContactsRepository {
    override fun fetchContactsFromProvider(useStorage: UseStorage): Completable =
        when (useStorage) {
            UseStorage.DATABASE -> {
                contactsProvider.getContactsFromPhonebook()
                    .map { it.asDatabaseContactsList() }
                    .flatMapCompletable { contactsDao.insertAllContacts(it) }
            }
            UseStorage.FILE -> {
                contactsProvider.getContactsFromPhonebook()
                    .map { it.asLocalContactsList() }
                    .flatMapCompletable { localContactsSource.writeContactsToFile(it) }
            }
        }

    override fun getContactsFromStorage(useStorage: UseStorage): Flowable<List<DomainContact>> =
        when (useStorage) {
            UseStorage.DATABASE -> {
                contactsDao.getContactsFlowable()
                    .map { it.asDomainContactsList() }
            }
            UseStorage.FILE -> {
                localContactsSource.getContactsFromFile()
                    .map { it.asDomainContactsList() }
                    .toFlowable()
            }
        }

    override fun updateContact(contact: DomainContact, useStorage: UseStorage): Completable =
        when (useStorage) {
            UseStorage.DATABASE -> {
                contactsDao.updateContact(
                    contact.asDatabaseContact()
                )
            }

            UseStorage.FILE -> {
                localContactsSource.updateContactWithId(
                    contact.asLocalContact()
                )
            }
        }

    override fun getContactWithIdFromStorage(
        id: Int,
        useStorage: UseStorage
    ): Single<DomainContact> = when (useStorage) {
        UseStorage.DATABASE -> {
            contactsDao.getContactById(id)
                .map { it.asDomainContact() }
        }

        UseStorage.FILE -> {
            localContactsSource.getContactFromFileById(id)
                .map { it.asDomainContact() }
        }
    }

    override fun removeContact(contact: DomainContact, useStorage: UseStorage): Completable =
        when (useStorage) {
            UseStorage.DATABASE -> {
                contactsDao.deleteContact(contact.id)
            }
            UseStorage.FILE -> {
                localContactsSource.deleteContactFromFileById(contact.id)
            }
        }
}