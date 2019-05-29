package com.projecttesting.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Binds [ViewModelProvider.Factory] to [DaggerViewModelFactory].
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory):
            ViewModelProvider.Factory
}
