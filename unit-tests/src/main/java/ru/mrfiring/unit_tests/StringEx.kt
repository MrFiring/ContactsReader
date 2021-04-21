package ru.mrfiring.unit_tests

fun String.hasLettersOnly(): Boolean = all{ it.isLetter() }