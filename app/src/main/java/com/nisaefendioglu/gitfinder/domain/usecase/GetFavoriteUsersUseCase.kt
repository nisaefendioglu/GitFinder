package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUsersUseCase @Inject constructor(
    private val userRepository: GithubRepository
) {
    operator fun invoke(): Flow<List<GithubUser>> {
        return userRepository.getFavoriteUsers()
    }
}