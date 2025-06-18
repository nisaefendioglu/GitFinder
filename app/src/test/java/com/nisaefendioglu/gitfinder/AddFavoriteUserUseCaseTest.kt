package com.nisaefendioglu.gitfinder

import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import com.nisaefendioglu.gitfinder.domain.usecase.AddFavoriteUserUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddFavoriteUserUseCaseTest {

    private lateinit var addFavoriteUserUseCase: AddFavoriteUserUseCase
    private val githubRepository: GithubRepository = mock()

    @Before
    fun setup() {
        addFavoriteUserUseCase = AddFavoriteUserUseCase(githubRepository)
    }

    @Test
    fun `invoke calls addFavoriteUser on repository`() = runTest {
        val user = GithubUser(1, "testuser", "url", "html")
        addFavoriteUserUseCase(user)
        verify(githubRepository).addFavoriteUser(user)
    }
}