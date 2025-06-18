package com.nisaefendioglu.gitfinder

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import com.nisaefendioglu.gitfinder.data.local.dao.FavoriteUserDao
import com.nisaefendioglu.gitfinder.data.local.database.AppDatabase
import com.nisaefendioglu.gitfinder.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue


@RunWith(AndroidJUnit4::class)
class FavoriteUserDaoTest {

    @get:Rule

    private lateinit var database: AppDatabase
    private lateinit var favoriteUserDao: FavoriteUserDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        favoriteUserDao = database.favoriteUserDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser_readsInList() = runBlocking {
        val user = FavoriteUserEntity(
            id = 1L,
            login = "testuser",
            avatarUrl = "url_avatar",
        )
        favoriteUserDao.insertFavoriteUser(user)

        val allUsers = favoriteUserDao.getAllFavoriteUsers().first()

        assertEquals(1, allUsers.size)
        assertEquals(user.login, allUsers[0].login)
    }

    @Test
    fun deleteUser_removesFromList() = runBlocking {
        val user1 = FavoriteUserEntity(1L, "user1", "url1")
        val user2 = FavoriteUserEntity(2L, "user2", "url2")

        favoriteUserDao.insertFavoriteUser(user1)
        favoriteUserDao.insertFavoriteUser(user2)

        favoriteUserDao.deleteFavoriteUserById(user1.id)

        val allUsers = favoriteUserDao.getAllFavoriteUsers().first()

        assertEquals(1, allUsers.size)
        assertEquals(user2.login, allUsers[0].login)
    }

    @Test
    fun isUserFavorite_returnsTrueForFavoriteUser() = runBlocking {
        val user = FavoriteUserEntity(1L, "testuser", "url")

        favoriteUserDao.insertFavoriteUser(user)

        val isFavorite = favoriteUserDao.isUserFavorite(user.id)

        assertTrue(isFavorite)
    }

    @Test
    fun isUserFavorite_returnsFalseForNonFavoriteUser() = runBlocking {
        val isFavorite = favoriteUserDao.isUserFavorite(99L)
        assertFalse(isFavorite)
    }
}