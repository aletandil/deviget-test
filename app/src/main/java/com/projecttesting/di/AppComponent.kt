package com.projecttesting.di

import com.projecttesting.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component is a bridge between Module(provider) and Activity/Fragment(Consumer) and it can have more than one module.
 *
 * The generated Component class will look through its modules and assign fields
 * annotated with @Inject
 *
 * <a href="https://google.github.io/dagger/android.html">Dagger Android</a>
 * <a href="https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2>Dagger 2 guide</a>
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        /**
         * Marks a method on a component builder or subcomponent builder that allows an instance to be bound
         * to some type within the component.
         *
         * Binding an instance is equivalent to passing an instance to a module constructor and providing
         * that instance, but is often more efficient. When possible, binding object instances should be
         * preferred to using module instances.
         */
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }

    override fun inject(app: App)

}
