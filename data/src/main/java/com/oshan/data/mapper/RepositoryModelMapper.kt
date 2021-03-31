package com.oshan.data.mapper

import com.oshan.data.fragment.RepositoryFragment
import com.oshan.domain.model.Language
import com.oshan.domain.model.Repository

object RepositoryModelMapper {
    fun fromGraphqlEntity(from: RepositoryFragment?) = if (from != null) Repository(
        id = from.id(),
        name = from.name(),
        description = from.description(),
        owner = RepoOwnerModelMapperImpl.fromGraphqlUserEntity(from = from.owner().fragments().repoOwnerFragment()),
        languages = toListOfLanguageModel(from.languages()?.nodes()),
        stargazerCount = from.stargazerCount()
    ) else {
        null
    }

    private fun toListOfLanguageModel(from: List<RepositoryFragment.Node>?): List<Language> {
        val listOfLanguages = ArrayList<Language>()
        from?.forEach {
            val languageFragment = it.fragments().languageFragment()
            listOfLanguages.add(Language(languageFragment.color(), languageFragment.name()))
        }
        return listOfLanguages
    }

}
