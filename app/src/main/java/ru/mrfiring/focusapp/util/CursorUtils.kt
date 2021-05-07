package ru.mrfiring.focusapp.util

import android.database.Cursor

fun Cursor.findColumnAndGetString(columnName: String): String? = getString(
    getColumnIndex(columnName)
)