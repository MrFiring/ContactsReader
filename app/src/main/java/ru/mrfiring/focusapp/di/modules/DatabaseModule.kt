package ru.mrfiring.focusapp.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.mrfiring.focusapp.data.database.ContactsDao
import ru.mrfiring.focusapp.data.database.ContactsDatabase
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideContactsDatabase(context: Context): ContactsDatabase {
        return Room.databaseBuilder(
            context,
            ContactsDatabase::class.java,
            "contacts_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(database: ContactsDatabase): ContactsDao = database.contactsDao

}