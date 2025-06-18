package com.nisaefendioglu.gitfinder

import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import com.nisaefendioglu.gitfinder.domain.usecase.RemoveFavoriteUserUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RemoveFavoriteUserUseCaseTest {

    private lateinit var removeFavoriteUserUseCase: RemoveFavoriteUserUseCase
    private val githubRepository: GithubRepository = mock()

    @Before
    fun setup() {
        removeFavoriteUserUseCase = RemoveFavoriteUserUseCase(githubRepository)
    }

    @Test
    fun `invoke calls removeFavoriteUser on repository`() = runTest {
        val userId = 1L
        removeFavoriteUserUseCase(userId)
        verify(githubRepository).removeFavoriteUser(userId)
    }
}