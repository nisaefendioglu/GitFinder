package com.nisaefendioglu.gitfinder.domain.model

data class GithubUser(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    var isFavorite: Boolean = false
)