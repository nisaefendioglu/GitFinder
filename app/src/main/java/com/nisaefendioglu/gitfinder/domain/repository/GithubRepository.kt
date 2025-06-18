package com.nisaefendioglu.gitfinder.domain.repository

import com.nisaefendioglu.gitfinder.domain.model.GithubRepo
import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.model.GithubUserDetail
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun searchUsers(query: String): List<GithubUser>
    suspend fun getUserDetail(username: String): GithubUserDetail
    suspend fun addFavoriteUser(user: GithubUser)
    suspend fun removeFavoriteUser(userId: Long)
    fun getFavoriteUsers(): Flow<List<GithubUser>>
    suspend fun isUserFavorite(userId: Long): Boolean
    suspend fun getReposByUsername(username: String): List<GithubRepo>
}