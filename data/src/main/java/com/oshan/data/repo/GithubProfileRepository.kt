package com.oshan.data.repo

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.oshan.data.datasource.GithubProfileDataSource
import com.oshan.data.mapper.RepositoryModelMapper
import com.oshan.data.mapper.RepoOwnerModelMapperImpl
import com.oshan.data.mapper.UserModelMapper
import com.oshan.domain.DomainLayerContract
import com.oshan.domain.Failure
import com.oshan.domain.model.Repository
import com.oshan.domain.model.UserProfileRequest
import com.oshan.domain.model.UserProfileResponse
import java.net.SocketTimeoutException

object GithubProfileRepository : DomainLayerContract.Data.DataRepository<UserProfileResponse> {

    lateinit var numberFactDataSource: GithubProfileDataSource

    @Throws(SocketTimeoutException::class)
    override suspend fun fetchGithubProfile(request: UserProfileRequest): Either<Failure, UserProfileResponse> {
        try {
            val response = numberFactDataSource.fetchGithubProfile(request = request)
            val body = response.data?.toString()


            val user = UserModelMapper.fromGraphqlUserEntity(
                response.data?.user()
            )

            val starredRepoList = ArrayList<Repository>()
            response.data?.user()?.starredRepositories()?.nodes()?.forEach {
                val repo = it.fragments().repositoryFragment()
                RepositoryModelMapper.fromGraphqlEntity(repo)?.let { it1 ->
                    starredRepoList.add(
                        it1
                    )
                }
            }

            val pinnedRepoList = ArrayList<Repository>()
            response.data?.user()?.pinnedItems()?.nodes()?.forEach {
                val repo = it.fragments().repositoryFragment()
                RepositoryModelMapper.fromGraphqlEntity(repo)?.let { it1 ->
                    pinnedRepoList.add(
                        it1
                    )
                }
            }

            val topRepoList = ArrayList<Repository>()
            response.data?.user()?.topRepositories()?.nodes()?.forEach {
                val repo = it.fragments().repositoryFragment()
                RepositoryModelMapper.fromGraphqlEntity(repo)?.let { it1 ->
                    topRepoList.add(
                        it1
                    )
                }
            }


            val userProfileResponse = UserProfileResponse(userData = user, starredRepos = starredRepoList, pinnedRepos = pinnedRepoList, topRepos = topRepoList)

            return userProfileResponse.right()


        } catch (e: Exception) {
            return Failure.ServerError(e.localizedMessage ?: "Server error").left()
        }
    }

}