package ru.mrfiring.focusapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.mrfiring.focusapp.di.components.ApplicationComponent
import ru.mrfiring.focusapp.di.components.DaggerApplicationComponent

class FocusApplication : DaggerApplication() {

    lateinit var appComponent: ApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        return appComponent
    }

}