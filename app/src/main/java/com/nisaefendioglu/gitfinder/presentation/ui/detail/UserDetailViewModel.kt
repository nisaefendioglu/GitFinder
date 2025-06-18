package com.nisaefendioglu.gitfinder.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.gitfinder.domain.model.GithubUser
import com.nisaefendioglu.gitfinder.domain.model.GithubUserDetail
import com.nisaefendioglu.gitfinder.domain.model.GithubRepo
import com.nisaefendioglu.gitfinder.domain.usecase.AddFavoriteUserUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.CheckUserFavoriteStatusUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.GetUserDetailUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.RemoveFavoriteUserUseCase
import com.nisaefendioglu.gitfinder.domain.usecase.GetReposByUsernameUseCase
import com.nisaefendioglu.gitfinder.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val addFavoriteUserUseCase: AddFavoriteUserUseCase,
    private val removeFavoriteUserUseCase: RemoveFavoriteUserUseCase,
    private val checkUserFavoriteStatusUseCase: CheckUserFavoriteStatusUseCase,
    private val getReposByUsernameUseCase: GetReposByUsernameUseCase
) : ViewModel() {
    private val _userDetail = MutableStateFlow<Resource<GithubUserDetail>>(Resource.Loading())
    val userDetail: StateFlow<Resource<GithubUserDetail>> = _userDetail.asStateFlow()

    private val _userRepos = MutableStateFlow<Resource<List<GithubRepo>>>(Resource.Loading())
    val userRepos: StateFlow<Resource<List<GithubRepo>>> = _userRepos.asStateFlow()


    fun getUserDetail(username: String, userId: Long) {
        viewModelScope.launch {
            _userDetail.value = Resource.Loading()
            try {
                val detail = getUserDetailUseCase(username)
                val isFavorite = checkUserFavoriteStatusUseCase(userId)
                _userDetail.value = Resource.Success(detail.copy(isFavorite = isFavorite))

                getRepos(username)

            } catch (e: Exception) {
                _userDetail.value = Resource.Error(e.localizedMessage ?: "Kullanıcı detayı alınamadı.")
            }
        }
    }

    private fun getRepos(username: String) {
        viewModelScope.launch {
            _userRepos.value = Resource.Loading()
            try {
                val repos = getReposByUsernameUseCase(username)
                _userRepos.value = Resource.Success(repos)
                if (repos.isEmpty()) {
                    _userRepos.value = Resource.Empty()

                }
            } catch (e: Exception) {
                _userRepos.value = Resource.Error(e.localizedMessage ?: "Kullanıcı repoları alınamadı.")
            }
        }
    }

    fun toggleFavoriteStatus() {
        viewModelScope.launch {
            val currentDetail = (_userDetail.value.data)
            currentDetail?.let {
                try {
                    if (it.isFavorite) {
                        removeFavoriteUserUseCase(it.id)
                    } else {
                        addFavoriteUserUseCase(GithubUser(it.id, it.login, it.avatarUrl, it.htmlUrl))
                    }
                    _userDetail.value = Resource.Success(it.copy(isFavorite = !it.isFavorite))
                } catch (e: Exception) {
                }
            }
        }
    }
}