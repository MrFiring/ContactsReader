package ru.mrfiring.focusapp.di.modules

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.mrfiring.focusapp.data.file.LocalContactsSource
import ru.mrfiring.focusapp.data.file.LocalContactsSourceImpl
import javax.inject.Singleton

@Module
abstract class LocalContactsSourceModule {

    companion object {
        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder().build()
    }

    @Binds
    @Singleton
    abstract fun provideLocalContactsSource(
        localContacts: LocalContactsSourceImpl
    ): LocalContactsSource

}