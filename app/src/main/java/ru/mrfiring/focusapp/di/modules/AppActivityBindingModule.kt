package ru.mrfiring.focusapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.mrfiring.focusapp.di.scopes.ActivityScoped
import ru.mrfiring.focusapp.presentation.MainActivity

@Module
abstract class AppActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun providesMainActivity(): MainActivity
}