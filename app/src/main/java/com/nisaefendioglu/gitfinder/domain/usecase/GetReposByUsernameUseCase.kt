package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.model.GithubRepo
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class GetReposByUsernameUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): List<GithubRepo> {
        return repository.getReposByUsername(username)
    }
}