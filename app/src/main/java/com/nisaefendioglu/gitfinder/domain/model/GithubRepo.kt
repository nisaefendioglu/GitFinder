package com.nisaefendioglu.gitfinder.domain.model

data class GithubRepo(
    val id: Long,
    val name: String,
    val htmlUrl: String,
    val description: String?,
    val stargazersCount: Int,
    val forks: Int,
    val language: String?
)