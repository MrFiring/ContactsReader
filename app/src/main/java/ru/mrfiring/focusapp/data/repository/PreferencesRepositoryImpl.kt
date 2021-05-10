package ru.mrfiring.focusapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.mrfiring.focusapp.domain.model.PreferencesKeys
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences
) : PreferencesRepository {
    override fun getBoolean(
        preferencesKey: PreferencesKeys,
        defaultValue: Boolean
    ): Single<Boolean> {
        return Single.fromCallable {
            preferences.getBoolean(preferencesKey.value, defaultValue)
        }.subscribeOn(Schedulers.io())
    }

    override fun saveBoolean(preferencesKey: PreferencesKeys, value: Boolean): Completable {
        return Completable.fromCallable {
            preferences.edit {
                putBoolean(preferencesKey.value, value)
                    .apply()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getInt(preferencesKey: PreferencesKeys, defaultValue: Int): Single<Int> {
        return Single.fromCallable {
            preferences.getInt(preferencesKey.value, defaultValue)
        }.subscribeOn(Schedulers.io())
    }

    override fun saveInt(preferencesKey: PreferencesKeys, value: Int): Completable {
        return Completable.fromCallable {
            preferences.edit {
                putInt(preferencesKey.value, value)
                    .apply()
            }
        }.subscribeOn(Schedulers.io())
    }
}