package ru.mrfiring.focusapp.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val SHARED_PREFS_NAME = "ru.mrfiring.focusapp.SHARED_PREFS"

@Module
abstract class AndroidModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )
}