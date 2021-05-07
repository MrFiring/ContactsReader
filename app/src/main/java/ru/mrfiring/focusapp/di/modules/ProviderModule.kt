package ru.mrfiring.focusapp.di.modules

import dagger.Module
import dagger.Provides
import ru.mrfiring.focusapp.data.provider.ContactsProvider
import ru.mrfiring.focusapp.data.provider.SystemContactsProviderImpl
import javax.inject.Singleton

@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideContactsProvider(
        systemContactsProviderImpl: SystemContactsProviderImpl
    ): ContactsProvider = systemContactsProviderImpl
}