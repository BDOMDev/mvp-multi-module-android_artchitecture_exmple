package com.oshan.data.mapper

import com.oshan.data.fragment.RepoOwnerFragment
import com.oshan.domain.model.RepoOwner
import com.oshan.domain.model.User

/**
 * Maps between Graphql result object and model.
 */
object RepoOwnerModelMapperImpl {
    fun fromGraphqlUserEntity(from: RepoOwnerFragment?) = if (from != null) RepoOwner(
        avatarUrl = from.avatarUrl().toString(),
        login = from.login()
    ) else null

}