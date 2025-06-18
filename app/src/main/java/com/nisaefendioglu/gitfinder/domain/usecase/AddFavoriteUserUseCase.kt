package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class AddFavoriteUserUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(user: GithubUser) {
        repository.addFavoriteUser(user)
    }
}