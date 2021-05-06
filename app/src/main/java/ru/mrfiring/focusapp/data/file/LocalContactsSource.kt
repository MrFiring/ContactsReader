package ru.mrfiring.focusapp.data.file

import io.reactivex.Completable
import io.reactivex.Single

interface LocalContactsSource {

    fun getContactsFromFile(): Single<List<LocalContact>>

    fun writeContactsToFile(): Completable

    fun getContactFromFileById(): Single<LocalContact>

    fun deleteContactFromFileById(): Completable

}