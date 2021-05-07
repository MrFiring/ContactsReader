package ru.mrfiring.focusapp.data.file

import io.reactivex.Completable
import io.reactivex.Single

interface LocalContactsSource {

    fun getContactsFromFile(): Single<List<LocalContact>>

    fun writeContactsToFile(list: List<LocalContact>): Completable

    fun getContactFromFileById(id: Int): Single<LocalContact>

    fun updateContactWithId(contact: LocalContact): Completable

    fun deleteContactFromFileById(id: Int): Completable

}