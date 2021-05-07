package ru.mrfiring.focusapp.data.mapper

import ru.mrfiring.focusapp.data.file.LocalContact
import ru.mrfiring.focusapp.domain.model.DomainContact

fun LocalContact.asDomainContact(): DomainContact = DomainContact(
    id = id,
    name = name,
    number = number
)

fun List<LocalContact>.asDomainContactsList(): List<DomainContact> = map { it.asDomainContact() }

fun DomainContact.asLocalContact(): LocalContact = LocalContact(
    id = id,
    name = name,
    number = number
)