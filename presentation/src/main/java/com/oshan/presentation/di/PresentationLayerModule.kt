package com.oshan.presentation.di

import com.oshan.presentation.ui.home.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Module(subcomponents = [HomeComponent::class])
object PresentationLayerModule

interface HomeComponentFactoryProvider {
    fun provideMainComponentFactory(): HomeComponent.Factory
}

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(module: HomeModule): HomeComponent
    }

    fun inject(activity: HomeActivity)

}

@Module
class HomeModule(private val activity: HomeActivity) {

    @ActivityScope
    @Provides
    @Named(HOME_VIEW_TAG)
    fun provideMainView(): HomeContract.View = activity

    @ActivityScope
    @Provides
    @Named(HOME_PRESENTER_TAG)
    fun provideMainPresenter(presenter: HomePresenter): HomeContract.Presenter = presenter

}