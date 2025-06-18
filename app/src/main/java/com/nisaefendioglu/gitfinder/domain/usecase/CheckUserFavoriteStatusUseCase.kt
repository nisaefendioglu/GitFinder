package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class CheckUserFavoriteStatusUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(userId: Long): Boolean {
        return repository.isUserFavorite(userId)
    }
}