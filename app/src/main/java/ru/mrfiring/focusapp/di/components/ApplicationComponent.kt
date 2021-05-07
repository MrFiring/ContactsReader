package ru.mrfiring.focusapp.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.mrfiring.focusapp.FocusApplication
import ru.mrfiring.focusapp.di.modules.*
import ru.mrfiring.focusapp.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        ProviderModule::class,
        LocalContactsSourceModule::class,
        RepositoryModule::class,
        AndroidInjectionModule::class,
        AppActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<FocusApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}