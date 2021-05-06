package ru.mrfiring.focusapp.data.provider

import io.reactivex.Single

interface SystemContactsProvider {
    fun getContactsFromPhonebook(): Single<List<ProviderContact>>
}