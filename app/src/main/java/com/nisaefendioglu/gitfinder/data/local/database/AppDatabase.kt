package com.nisaefendioglu.gitfinder.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nisaefendioglu.gitfinder.data.local.dao.FavoriteUserDao
import com.nisaefendioglu.gitfinder.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}