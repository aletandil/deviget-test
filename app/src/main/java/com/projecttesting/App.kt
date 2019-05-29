package com.projecttesting

import android.app.Activity
import com.projecttesting.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject


class App : DaggerApplication(), HasActivityInjector {

    private val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    override fun applicationInjector() = applicationInjector
}
