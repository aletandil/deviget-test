package com.projecttesting.di

import android.content.Context
import android.content.SharedPreferences
import com.projecttesting.App
import com.projecttesting.data.local.AppDatabase
import com.projecttesting.data.repositories.RidersRepository
import com.projecttesting.data.services.RidersService
import com.projecttesting.domain.LoadRiderUseCase
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
        remoteRidersSource: RidersService,
        appDatabase: AppDatabase
    ): RidersRepository =
        RidersRepository(remoteRidersSource, appDatabase.ridersDao())

    @Singleton
    @Provides
    open fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providesLoadRiderUseCase(
        ridersRepository: RidersRepository
    ): LoadRiderUseCase = LoadRiderUseCase(ridersRepository)
}
