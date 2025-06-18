package com.nisaefendioglu.gitfinder.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepoResponse(
    val id: Long,
    val name: String,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    val forks: Int,
    val language: String?
)