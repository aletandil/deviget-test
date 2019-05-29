package com.projecttesting.di

import com.projecttesting.ui.main.MainActivity
import com.projecttesting.ui.main.MainFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    /**
     * Generates an {@link AndroidInjector} for the return type of this method. The injector is
     * implemented with a {@link dagger.Subcomponent} and will be a child of the {@link dagger.Module}'s
     * component.
     *
     * <p>This annotation must be applied to an abstract method in a {@link dagger.Module} that returns
     * a concrete Android framework type (e.g. {@code FooActivity}, {@code BarFragment}, {@code
     * MyService}, etc). The method should have no parameters.
     *
     * <p>For more information, see <a href="https://google.github.io/dagger/android">the docs</a>
     */
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainFragmentModule::class
        ]
    )
    internal abstract fun bindMainActivity(): MainActivity

}
