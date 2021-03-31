package com.oshan.githubprofile.di

import android.content.Context
import com.oshan.data.di.DataSourceModule
import com.oshan.data.di.RepositoryModule
import com.oshan.domain.di.UseCaseModule
import com.oshan.presentation.di.ApplicationScope
import com.oshan.presentation.di.HomeComponent
import com.oshan.presentation.di.PresentationLayerModule
import dagger.Component
import dagger.Module
import dagger.Provides

@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationLayerModule::class, UseCaseModule::class,
        RepositoryModule::class, DataSourceModule::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule): ApplicationComponent
    }

    // downstream dependent components need data types to be exposed
    // 'subcomponents' do not need this exposure! :) (i.e. 'Context' is automatically reachable!)
    //fun splashComponentFactory(): SplashComponent.Factory
    fun mainComponentFactory(): HomeComponent.Factory

}

@Module
class UtilsModule(private val ctx: Context) {

    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context = ctx

}