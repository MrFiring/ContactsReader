package ru.mrfiring.focusapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val number: String
)