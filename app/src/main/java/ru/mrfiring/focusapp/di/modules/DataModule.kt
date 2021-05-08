package ru.mrfiring.focusapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val SHARED_PREFS_NAME = "ru.mrfiring.focusapp.SHARED_PREFS"

@Module(
    includes = [
        DatabaseModule::class,
        ProviderModule::class,
        LocalContactsSourceModule::class
    ]
)
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )
}