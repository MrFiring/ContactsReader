package ru.mrfiring.focusapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.mrfiring.focusapp.domain.model.PreferencesKeys

interface PreferencesRepository {
    fun getBoolean(preferencesKey: PreferencesKeys, defaultValue: Boolean): Single<Boolean>
    fun saveBoolean(preferencesKey: PreferencesKeys, value: Boolean): Completable
}