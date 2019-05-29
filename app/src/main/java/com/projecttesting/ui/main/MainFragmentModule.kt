package com.projecttesting.ui.main

import androidx.lifecycle.ViewModel
import com.projecttesting.di.FragmentScoped
import com.projecttesting.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MainFragment] are defined.
 */
@Module
internal abstract class MainFragmentModule {

    /**
     * Generates an [AndroidInjector]
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    /**
     * Get a [MainViewModel] from [DaggerViewModelFactory]
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindScheduleViewModel(viewModel: MainViewModel): ViewModel

}
