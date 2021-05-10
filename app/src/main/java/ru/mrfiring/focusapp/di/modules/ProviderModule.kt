package ru.mrfiring.focusapp.di.modules

import dagger.Binds
import dagger.Module
import ru.mrfiring.focusapp.data.provider.ContactsProvider
import ru.mrfiring.focusapp.data.provider.SystemContactsProviderImpl
import javax.inject.Singleton

@Module
interface ProviderModule {

    @Binds
    @Singleton
    fun provideContactsProvider(
        systemContactsProviderImpl: SystemContactsProviderImpl
    ): ContactsProvider
}