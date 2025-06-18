package com.nisaefendioglu.gitfinder.di

import com.nisaefendioglu.gitfinder.data.local.dao.FavoriteUserDao
import com.nisaefendioglu.gitfinder.data.remote.api.GithubApiService
import com.nisaefendioglu.gitfinder.data.repository.GithubRepositoryImpl
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubRepository(
        apiService: GithubApiService,
        favoriteUserDao: FavoriteUserDao
    ): GithubRepository {
        return GithubRepositoryImpl(apiService, favoriteUserDao)
    }
}