package ru.mrfiring.focusapp.data.provider

import io.reactivex.Single

interface ContactsProvider {
    fun getContactsFromPhonebook(): Single<List<ProviderContact>>
}