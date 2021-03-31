package com.oshan.domain.di

import com.oshan.domain.DomainLayerContract
import com.oshan.domain.model.UserProfileRequest
import com.oshan.domain.model.UserProfileResponse
import com.oshan.domain.usecase.FETCH_GITHUB_PROFILE_UC_TAG
import com.oshan.domain.usecase.FetchGithubProfileUc
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object UseCaseModule {

    @Provides
    @Named(FETCH_GITHUB_PROFILE_UC_TAG)
    fun provideFetchGithubProfileUc(useCase: FetchGithubProfileUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<UserProfileRequest, UserProfileResponse> =
        useCase

}