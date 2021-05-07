package ru.mrfiring.focusapp.data.file

import android.content.Context
import com.squareup.moshi.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okio.Okio

private const val FILE_NAME = "contacts_storage.json"

class LocalContactsSourceImpl(
    private val moshi: Moshi,
    private val context: Context
) : LocalContactsSource {

    private val type = Types.newParameterizedType(List::class.java, LocalContact::class.java)
    private val contactsAdapter: JsonAdapter<List<LocalContact>> = moshi.adapter(type)


    override fun getContactsFromFile(): Single<List<LocalContact>> = Single.fromCallable {

        val inputStream = context.openFileInput(FILE_NAME)
        JsonReader.of(Okio.buffer(Okio.source(inputStream))).use {
            contactsAdapter.fromJson(it)?.let { contacts ->
                return@fromCallable contacts
            }
        }
    }

    override fun writeContactsToFile(list: List<LocalContact>): Completable =
        Completable.fromAction {
            val outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            JsonWriter.of(Okio.buffer(Okio.sink(outputStream))).use {
                contactsAdapter.toJson(it, list)
            }
        }

    override fun getContactFromFileById(id: Int): Single<LocalContact> = getContactsFromFile()
        .flatMapObservable { Observable.fromIterable(it) }
        .filter { it.id == id }
        .firstOrError()


    override fun updateContactWithId(contact: LocalContact): Completable = getContactsFromFile()
        .flatMapObservable { Observable.fromIterable(it) }
        .map {
            if (it.id == contact.id) {
                contact
            } else {
                it
            }
        }
        .toList()
        .flatMapCompletable { writeContactsToFile(it) }

    override fun deleteContactFromFileById(id: Int): Completable = getContactsFromFile()
        .flatMapObservable { Observable.fromIterable(it) }
        .filter { it.id != id }
        .toList()
        .flatMapCompletable { writeContactsToFile(it) }
}