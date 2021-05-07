package ru.mrfiring.focusapp.data.file

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocalContact(
    val id: Int,
    val name: String,
    val number: String
)
