package com.nisaefendioglu.gitfinder.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.repository.GithubRepository
import com.nisaefendioglu.gitfinder.domain.usecase.AddFavoriteUserUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.RemoveFavoriteUserUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.SearchUsersUseCase
import com.nisaefendioglu.gitfinder.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val addFavoriteUserUseCase: AddFavoriteUserUseCase,
    private val removeFavoriteUserUseCase: RemoveFavoriteUserUseCase,
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _users = MutableStateFlow<Resource<List<GithubUser>>>(Resource.Empty())
    val users: StateFlow<Resource<List<GithubUser>>> = _users.asStateFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _users.value = Resource.Loading()
            try {
                val result = searchUsersUseCase(query)
                val usersWithFavoriteStatus = result.map { user ->
                    user.copy(isFavorite = githubRepository.isUserFavorite(user.id))
                }
                _users.value = Resource.Success(usersWithFavoriteStatus)
            } catch (e: Exception) {
                _users.value = Resource.Error(e.localizedMessage ?: "Bilinmeyen bir hata oluştu.")
            }
        }
    }

    fun toggleFavoriteStatus(user: GithubUser) {
        viewModelScope.launch {
            try {
                if (user.isFavorite) {
                    removeFavoriteUserUseCase(user.id)
                } else {
                    addFavoriteUserUseCase(user)
                }
                val currentUsers = _users.value.data.orEmpty()
                val updatedUsers = currentUsers.map { githubUser ->
                    if (githubUser.id == user.id) {
                        githubUser.copy(isFavorite = !user.isFavorite)
                    } else {
                        githubUser
                    }
                }
                _users.value = Resource.Success(updatedUsers)

            } catch (e: Exception) {
                _users.value = Resource.Error(e.localizedMessage ?: "Favori işlemi başarısız oldu.")
            }
        }
    }
}