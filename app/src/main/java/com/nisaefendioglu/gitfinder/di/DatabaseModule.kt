package com.nisaefendioglu.gitfinder.di

import android.content.Context
import androidx.room.Room
import com.nisaefendioglu.gitfinder.data.local.dao.FavoriteUserDao
import com.nisaefendioglu.gitfinder.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "git_finder_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteUserDao(appDatabase: AppDatabase): FavoriteUserDao {
        return appDatabase.favoriteUserDao()
    }
}