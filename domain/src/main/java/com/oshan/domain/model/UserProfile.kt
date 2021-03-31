package com.oshan.domain.model

data class UserProfileResponse(
        var userData: User?,
        var starredRepos: ArrayList<Repository>,
        var pinnedRepos: ArrayList<Repository>,
        var topRepos: ArrayList<Repository>
)

data class UserProfileRequest(
        var login: String
)