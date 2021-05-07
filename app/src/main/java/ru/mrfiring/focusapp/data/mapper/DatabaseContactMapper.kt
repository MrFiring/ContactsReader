package ru.mrfiring.focusapp.data.mapper

import ru.mrfiring.focusapp.data.database.DatabaseContact
import ru.mrfiring.focusapp.domain.model.DomainContact

fun List<DatabaseContact>.asDomainContactsList(): List<DomainContact> = map {
    it.asDomainContact()
}

fun DatabaseContact.asDomainContact(): DomainContact = DomainContact(
    id = id,
    name = name,
    number = number
)

fun DomainContact.asDatabaseContact(): DatabaseContact = DatabaseContact(
    id = id,
    name = name,
    number = number
)