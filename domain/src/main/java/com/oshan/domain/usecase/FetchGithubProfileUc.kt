package com.oshan.domain.usecase

import arrow.core.Either
import arrow.core.left
import com.oshan.domain.DomainLayerContract
import com.oshan.domain.DomainLayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.oshan.domain.Failure
import com.oshan.domain.model.UserProfileRequest
import com.oshan.domain.model.UserProfileResponse
import javax.inject.Inject
import javax.inject.Named

const val FETCH_GITHUB_PROFILE_UC_TAG = "fetchGithubProfile"

class FetchGithubProfileUc @Inject constructor(
    @Named(DATA_REPOSITORY_TAG)
    private val numberDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.DataRepository<UserProfileResponse>
) : DomainLayerContract.Presentation.UseCase<UserProfileRequest, UserProfileResponse> {

    override suspend fun run(params: UserProfileRequest?): Either<Failure, UserProfileResponse> =
        params?.let {
            numberDataRepository.fetchGithubProfile(request = params)
        } ?: run {
            Failure.InputParamsError().left()
        }

}