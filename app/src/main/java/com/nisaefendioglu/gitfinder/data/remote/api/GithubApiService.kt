package com.nisaefendioglu.gitfinder.data.remote.api
import com.nisaefendioglu.gitfinder.data.remote.model.RepoResponse
import com.nisaefendioglu.gitfinder.data.remote.model.UserDetailResponse
import com.nisaefendioglu.gitfinder.data.remote.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): UserSearchResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailResponse

    @GET("users/{username}/repos")
    suspend fun getReposByUsername(@Path("username") username: String): List<RepoResponse>
}