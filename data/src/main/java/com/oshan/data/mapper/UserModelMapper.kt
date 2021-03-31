package com.oshan.data.mapper

import com.oshan.data.GitHubProfileQuery
import com.oshan.data.fragment.RepoOwnerFragment
import com.oshan.domain.model.RepoOwner
import com.oshan.domain.model.User

object UserModelMapper {
        fun fromGraphqlUserEntity(from: GitHubProfileQuery.User?) = if (from != null) User(
            avatarUrl = from.avatarUrl().toString(),
            login = from.login(),
            email = from.email(),
            name = from.name(),
            followersCount = from.followers().totalCount(),
            followingCount = from.following().totalCount()
        ) else null

}