package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(query: String): List<GithubUser> {
        val users = repository.searchUsers(query)
        val usersWithFavoriteStatus = users.map { user ->
            user.copy(isFavorite = repository.isUserFavorite(user.id))
        }
        return usersWithFavoriteStatus
    }
}