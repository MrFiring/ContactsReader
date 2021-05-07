package ru.mrfiring.focusapp.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import ru.mrfiring.focusapp.data.file.LocalContactsSource
import ru.mrfiring.focusapp.data.file.LocalContactsSourceImpl
import javax.inject.Singleton

@Module
class LocalContactsSourceModule {

    @Provides
    @Singleton
    fun provideLocalContactsSource(
        localContacts: LocalContactsSourceImpl
    ): LocalContactsSource = localContacts

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

}