package ru.mrfiring.focusapp.di.modules

import dagger.Module
import dagger.Provides
import ru.mrfiring.focusapp.data.repository.ContactsRepositoryImpl
import ru.mrfiring.focusapp.data.repository.PreferencesRepositoryImpl
import ru.mrfiring.focusapp.domain.repository.ContactsRepository
import ru.mrfiring.focusapp.domain.repository.PreferencesRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideContactsRepository(
        repository: ContactsRepositoryImpl
    ): ContactsRepository = repository

    @Provides
    @Singleton
    fun providePrefsRepository(
        repository: PreferencesRepositoryImpl
    ): PreferencesRepository = repository
}