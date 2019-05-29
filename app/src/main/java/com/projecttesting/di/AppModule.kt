package com.projecttesting.di

import android.content.Context
import android.content.SharedPreferences
import com.projecttesting.App
import com.projecttesting.data.local.AppDatabase
import com.projecttesting.data.repositories.EntryRepository
import com.projecttesting.data.services.EntriesService
import com.projecttesting.domain.LoadTopEntriesUseCase
import com.projecttesting.domain.MarkReadedEntryUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * App wide dependency provider
 *
 * Classes annotated with @Module are responsible for providing objects which can be injected.
 *
 * @Provides lets Dagger know to use the annotated method when the method's return type is needed
 */
@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideContext(application: App): Context = application

    @Provides
    open fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("com.projecttestingsharedprefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    open fun provideRidersRepository(
        remoteRidersSource: EntriesService,
        appDatabase: AppDatabase
    ): EntryRepository =
        EntryRepository(remoteRidersSource, appDatabase.ridersDao())

    @Singleton
    @Provides
    open fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providesLoadRiderUseCase(
        ridersRepository: EntryRepository
    ): LoadTopEntriesUseCase = LoadTopEntriesUseCase(ridersRepository)

    @Singleton
    @Provides
    fun providesMarkEntryReadedUseCase(
        ridersRepository: EntryRepository
    ): MarkReadedEntryUseCase = MarkReadedEntryUseCase(ridersRepository)
}
