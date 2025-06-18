package com.nisaefendioglu.gitfinder.domain.usecase

import com.nisaefendioglu.gitfinder.domain.model.GithubUserDetail
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): GithubUserDetail {
        val userDetail = repository.getUserDetail(username)
        return userDetail.copy(isFavorite = repository.isUserFavorite(userDetail.id))
    }
}