package ru.mrfiring.focusapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class AndroidModule {

    @Binds
    abstract fun bindContext(application: Application): Context

}