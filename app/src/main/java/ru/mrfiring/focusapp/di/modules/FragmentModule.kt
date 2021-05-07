package ru.mrfiring.focusapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.mrfiring.focusapp.di.scopes.FragmentScoped
import ru.mrfiring.focusapp.presentation.detail.DetailFragment
import ru.mrfiring.focusapp.presentation.home.HomeFragment

@Module
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment
}