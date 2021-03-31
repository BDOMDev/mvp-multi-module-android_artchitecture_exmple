package com.oshan.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.oshan.data.GitHubProfileQuery
import com.oshan.domain.model.UserProfileRequest
import javax.inject.Inject

interface GithubProfileDataSource {

    companion object {
        const val BASE_URL = "https://api.github.com/graphql"
        const val GITHUB_PROFILE_DATA_SOURCE_TAG = "githubDataSource"
    }

    suspend fun fetchGithubProfile(request: UserProfileRequest): Response<GitHubProfileQuery.Data>

}


class GithubApiDataSource @Inject constructor(private val apolloClient: ApolloClient) :
    GithubProfileDataSource {

    override suspend fun fetchGithubProfile(request: UserProfileRequest): Response<GitHubProfileQuery.Data> {
        val gitHubProfileDataQuery = GitHubProfileQuery.builder().login(request.login).build()
        return apolloClient.query(gitHubProfileDataQuery).await()
    }

}