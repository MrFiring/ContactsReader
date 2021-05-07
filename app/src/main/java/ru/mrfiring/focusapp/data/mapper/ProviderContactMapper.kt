package ru.mrfiring.focusapp.data.mapper

import ru.mrfiring.focusapp.data.database.DatabaseContact
import ru.mrfiring.focusapp.data.file.LocalContact
import ru.mrfiring.focusapp.data.provider.ProviderContact

fun List<ProviderContact>.asLocalContactsList(): List<LocalContact> =
    mapIndexed { i, contact ->
        LocalContact(
            id = i,
            name = contact.name,
            number = contact.number
        )
    }

fun List<ProviderContact>.asDatabaseContactsList(): List<DatabaseContact> =
    mapIndexed { i, contact ->
        DatabaseContact(
            id = i,
            name = contact.name,
            number = contact.number
        )
    }