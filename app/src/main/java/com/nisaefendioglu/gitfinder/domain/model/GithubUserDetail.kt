package com.nisaefendioglu.gitfinder.domain.model

data class GithubUserDetail(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val publicRepos: Int,
    var isFavorite: Boolean = false
)