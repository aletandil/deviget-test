package com.projecttesting.di

import android.content.Context
import android.content.SharedPreferences
import com.projecttesting.App
import com.projecttesting.data.local.AppDatabase
import com.projecttesting.data.repositories.EntryRepository
import com.projecttesting.data.services.EntriesService
import com.projecttesting.domain.DismissEntryUseCase
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
    open fun provideEntryRepository(
        remoteEntriesSource: EntriesService,
        appDatabase: AppDatabase
    ): EntryRepository =
        EntryRepository(remoteEntriesSource, appDatabase.entriesDao())

    @Singleton
    @Provides
    open fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providesLoadTopEntriesUseCase(
        entryRepository: EntryRepository
    ): LoadTopEntriesUseCase = LoadTopEntriesUseCase(entryRepository)

    @Singleton
    @Provides
    fun providesMarkEntryReadedUseCase(
        entryRepository: EntryRepository
    ): MarkReadedEntryUseCase = MarkReadedEntryUseCase(entryRepository)

    @Singleton
    @Provides
    fun providesDismissEntryReadedUseCase(
        entryRepository: EntryRepository
    ): DismissEntryUseCase = DismissEntryUseCase(entryRepository)
}
