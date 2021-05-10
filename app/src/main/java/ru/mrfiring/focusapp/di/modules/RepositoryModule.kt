package ru.mrfiring.focusapp.di.modules

import dagger.Binds
import dagger.Module
import ru.mrfiring.focusapp.data.repository.ContactsRepositoryImpl
import ru.mrfiring.focusapp.data.repository.PreferencesRepositoryImpl
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideContactsRepository(
        repository: ContactsRepositoryImpl
    ): ContactsRepository

    @Binds
    @Singleton
    fun providePrefsRepository(
        repository: PreferencesRepositoryImpl
    ): PreferencesRepository
}