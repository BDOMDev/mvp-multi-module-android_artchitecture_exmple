package com.oshan.domain.model

data class User(
    var avatarUrl: String?,
    var login: String?,
    var email: String?,
    var name: String?,
    var followersCount: Int,
    var followingCount: Int
) {

}