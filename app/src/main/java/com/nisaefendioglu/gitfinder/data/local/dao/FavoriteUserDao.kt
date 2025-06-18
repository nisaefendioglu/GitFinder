package com.nisaefendioglu.gitfinder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nisaefendioglu.gitfinder.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: FavoriteUserEntity)

    @Query("DELETE FROM favorite_users WHERE id = :userId")
    suspend fun deleteFavoriteUserById(userId: Long)

    @Query("SELECT * FROM favorite_users")
    fun getAllFavoriteUsers(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_users WHERE id = :userId LIMIT 1)")
    suspend fun isUserFavorite(userId: Long): Boolean
}