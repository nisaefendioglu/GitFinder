package com.nisaefendioglu.gitfinder

import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import com.nisaefendioglu.gitfinder.domain.usecase.CheckUserFavoriteStatusUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class CheckUserFavoriteStatusUseCaseTest {

    private lateinit var checkUserFavoriteStatusUseCase: CheckUserFavoriteStatusUseCase
    private val githubRepository: GithubRepository = mock()

    @Before
    fun setup() {
        checkUserFavoriteStatusUseCase = CheckUserFavoriteStatusUseCase(githubRepository)
    }

    @Test
    fun `invoke returns true if user is favorite`() = runTest {
        val userId = 1L
        whenever(githubRepository.isUserFavorite(userId)).thenReturn(true)

        val result = checkUserFavoriteStatusUseCase(userId)
        assertTrue(result)
        verify(githubRepository).isUserFavorite(userId)
    }

    @Test
    fun `invoke returns false if user is not favorite`() = runTest {
        val userId = 1L
        whenever(githubRepository.isUserFavorite(userId)).thenReturn(false)

        val result = checkUserFavoriteStatusUseCase(userId)
        assertFalse(result)
        verify(githubRepository).isUserFavorite(userId)
    }
}