package com.oshan.domain.model

data class Repository (
    var id: String,
    var name: String,
    var description: String?,
    var owner: RepoOwner?,
    var languages: List<Language>,
    var stargazerCount: Int = 0
)

data class Language(
    var color: String?,
    var name: String
)