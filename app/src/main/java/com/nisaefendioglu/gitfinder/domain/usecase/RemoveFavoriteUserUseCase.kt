package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class RemoveFavoriteUserUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(userId: Long) {
        repository.removeFavoriteUser(userId)
    }
}