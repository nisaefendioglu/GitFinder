package com.nisaefendioglu.gitfinder

import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import app.cash.turbine.test
import com.nisaefendioglu.gitfinder.domain.usecase.GetFavoriteUsersUseCase
import org.junit.Assert.assertEquals

class GetFavoriteUsersUseCaseTest {

    private lateinit var getFavoriteUsersUseCase: GetFavoriteUsersUseCase
    private val githubRepository: GithubRepository = mock()

    @Before
    fun setup() {
        getFavoriteUsersUseCase = GetFavoriteUsersUseCase(githubRepository)
    }

    @Test
    fun `invoke returns flow of favorite users from repository`() = runTest {
        val favoriteUsers = listOf(
            GithubUser(1, "user1", "url1", "html1"),
            GithubUser(2, "user2", "url2", "html2")
        )
        whenever(githubRepository.getFavoriteUsers()).thenReturn(flowOf(favoriteUsers))

        getFavoriteUsersUseCase().test {
            assertEquals(favoriteUsers, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify(githubRepository).getFavoriteUsers()
    }

    @Test
    fun `invoke returns empty list when no favorites`() = runTest {
        whenever(githubRepository.getFavoriteUsers()).thenReturn(flowOf(emptyList()))

        getFavoriteUsersUseCase().test {
            assertEquals(emptyList<GithubUser>(), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify(githubRepository).getFavoriteUsers()
    }
}