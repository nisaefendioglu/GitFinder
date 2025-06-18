package com.nisaefendioglu.gitfinder.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.usecase.GetFavoriteUsersUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.RemoveFavoriteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
    private val removeFavoriteUserUseCase: RemoveFavoriteUserUseCase
) : ViewModel() {

    private val _favoriteUsers = MutableStateFlow<List<GithubUser>>(emptyList())
    val favoriteUsers: StateFlow<List<GithubUser>> = _favoriteUsers.asStateFlow()

    init {
        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {
        viewModelScope.launch {
            getFavoriteUsersUseCase().collectLatest { users ->
                _favoriteUsers.value = users
            }
        }
    }

    fun removeFavoriteUser(user: GithubUser) {
        viewModelScope.launch {
            try {
                removeFavoriteUserUseCase(user.id)
            } catch (e: Exception) {

            }
        }
    }
}