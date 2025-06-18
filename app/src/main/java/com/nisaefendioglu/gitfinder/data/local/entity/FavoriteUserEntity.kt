package com.nisaefendioglu.gitfinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUserEntity(
    @PrimaryKey val id: Long,
    val login: String,
    val avatarUrl: String
)