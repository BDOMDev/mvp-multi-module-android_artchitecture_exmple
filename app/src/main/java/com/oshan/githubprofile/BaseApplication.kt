package com.oshan.githubprofile

import android.app.Application
import com.oshan.githubprofile.di.ApplicationComponent
import com.oshan.githubprofile.di.DaggerApplicationComponent
import com.oshan.githubprofile.di.UtilsModule
import com.oshan.presentation.di.HomeComponent
import com.oshan.presentation.di.HomeComponentFactoryProvider


/**
 * This class serves as entry point to the app.
 * General tool configurations such as 'Dagger' for dependency injection
 */
class BaseApplication : Application(), HomeComponentFactoryProvider {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        /*
         'ApplicationComponent' is created including all data every associated component needs.
         Specifically, 'modules' parameters refer to those which demand external variables (mostly
         'Context' instances).
         */
        appComponent = DaggerApplicationComponent.factory().create(modules = UtilsModule(ctx = this))
    }
    
//    override fun provideSplashComponentFactory(): SplashComponent.Factory =
//        appComponent.splashComponentFactory()

    override fun provideMainComponentFactory(): HomeComponent.Factory =
        appComponent.mainComponentFactory()

}