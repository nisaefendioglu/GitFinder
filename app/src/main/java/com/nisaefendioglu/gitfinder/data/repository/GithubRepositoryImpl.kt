package com.nisaefendioglu.gitfinder.data.repository

import com.nisaefendioglu.gitfinder.data.local.dao.FavoriteUserDao
import com.nisaefendioglu.gitfinder.data.local.entity.FavoriteUserEntity
import com.nisaefendioglu.gitfinder.data.remote.api.GithubApiService
import com.nisaefendioglu.gitfinder.data.remote.model.UserDto
import com.nisaefendioglu.gitfinder.data.remote.model.UserDetailResponse
import com.nisaefendioglu.gitfinder.domain.model.GithubRepo
import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.model.GithubUserDetail
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService,
    private val favoriteUserDao: FavoriteUserDao
) : GithubRepository {

    override suspend fun searchUsers(query: String): List<GithubUser> {
        return apiService.searchUsers(query).items.map { it.toDomainUser() }
    }

    override suspend fun getUserDetail(username: String): GithubUserDetail {
        return apiService.getUserDetail(username).toDomainUserDetail()
    }

    override suspend fun addFavoriteUser(user: GithubUser) {
        favoriteUserDao.insertFavoriteUser(user.toEntity())
    }

    override suspend fun removeFavoriteUser(userId: Long) {
        favoriteUserDao.deleteFavoriteUserById(userId)
    }

    override fun getFavoriteUsers(): Flow<List<GithubUser>> {
        return favoriteUserDao.getAllFavoriteUsers().map { entities ->
            entities.map { it.toDomainUser() }
        }
    }

    override suspend fun isUserFavorite(userId: Long): Boolean {
        return favoriteUserDao.isUserFavorite(userId)
    }

    override suspend fun getReposByUsername(username: String): List<GithubRepo> {
        return apiService.getReposByUsername(username).map { it.toDomainRepo() }
    }
}

fun UserDto.toDomainUser(): GithubUser {
    return GithubUser(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl
    )
}

fun UserDetailResponse.toDomainUserDetail(): GithubUserDetail {
    return GithubUserDetail(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl,
        name = this.name,
        company = this.company,
        blog = this.blog,
        location = this.location,
        email = this.email,
        bio = this.bio,
        followers = this.followers,
        following = this.following,
        publicRepos = this.publicRepos
    )
}

fun GithubUser.toEntity(): FavoriteUserEntity {
    return FavoriteUserEntity(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl
    )
}

fun FavoriteUserEntity.toDomainUser(): GithubUser {
    return GithubUser(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = "",
        isFavorite = true
    )
}

fun com.nisaefendioglu.gitfinder.data.remote.model.RepoResponse.toDomainRepo(): GithubRepo {
    return GithubRepo(
        id = this.id,
        name = this.name,
        htmlUrl = this.htmlUrl,
        description = this.description,
        stargazersCount = this.stargazersCount,
        forks = this.forks,
        language = this.language
    )
}