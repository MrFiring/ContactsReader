package ru.mrfiring.focusapp.domain

enum class UseStorage(val value: Int) {
    FILE(0),
    DATABASE(1);

    fun next(): UseStorage = when (value) {
        0 -> DATABASE
        1 -> FILE
        else -> DATABASE
    }

    companion object {
        fun parse(type: Int): UseStorage = when (type) {
            0 -> FILE
            1 -> DATABASE
            else -> DATABASE
        }
    }
}