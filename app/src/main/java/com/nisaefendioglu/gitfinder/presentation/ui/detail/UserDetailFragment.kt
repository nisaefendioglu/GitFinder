package com.nisaefendioglu.gitfinder.presentation.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nisaefendioglu.gitfinder.R
import com.nisaefendioglu.gitfinder.databinding.FragmentUserDetailBinding
import com.nisaefendioglu.gitfinder.domain.model.GithubUserDetail
import com.nisaefendioglu.gitfinder.presentation.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()

    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetail(args.username, args.userId)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        repoAdapter = RepoAdapter { repo ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl)))
        }
        binding.reposRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { collectUserDetail() }
                launch { collectUserRepos() }
            }
        }
    }

    private suspend fun collectUserDetail() {
        viewModel.userDetail.collect { resource ->
            binding.progressBar.isVisible = resource is Resource.Loading
            binding.errorText.isVisible = resource is Resource.Error
            binding.detailContentGroup.isVisible = resource is Resource.Success

            when (resource) {
                is Resource.Success -> resource.data?.let { bindUserDetail(it) }
                is Resource.Error   -> showError(resource.message)
                else                -> Unit
            }
        }
    }

    private suspend fun collectUserRepos() {
        viewModel.userRepos.collect { resource ->
            binding.reposRecyclerView.isVisible = resource is Resource.Success
            binding.reposTitleTextView.isVisible = resource is Resource.Success || resource is Resource.Empty
            binding.noReposTextView.isVisible = resource is Resource.Empty

            when (resource) {
                is Resource.Success -> repoAdapter.submitList(resource.data)
                is Resource.Error   -> showRepoError(resource.message)
                is Resource.Empty   -> showEmptyRepoMessage()
                else                -> hideReposViews()
            }
        }
    }

    private fun bindUserDetail(userDetail: GithubUserDetail) = with(binding) {
        Glide.with(userAvatarImageView.context)
            .load(userDetail.avatarUrl)
            .placeholder(R.drawable.ic_default_avatar)
            .circleCrop()
            .into(userAvatarImageView)

        userNameTextView.text = userDetail.login
        userRealNameTextView.text = userDetail.name ?: "N/A"
        userBioTextView.text = userDetail.bio ?: getString(R.string.no_bio_available)

        followersCountTextView.text = getString(R.string.followers_count, userDetail.followers)
        followingCountTextView.text = getString(R.string.following_count, userDetail.following)
        publicReposTextView.text = getString(R.string.public_repos_count, userDetail.publicRepos)

        profileLinkButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(userDetail.htmlUrl)))
        }

        updateFavoriteButton(userDetail.isFavorite)
        favoriteButton.setOnClickListener {
            viewModel.toggleFavoriteStatus()
        }
    }


    private fun updateFavoriteButton(isFavorite: Boolean) {
        val iconRes = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
        binding.favoriteButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), iconRes))
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: getString(R.string.default_error_message), Toast.LENGTH_SHORT).show()
        binding.errorText.text = message
    }

    private fun showRepoError(message: String?) {
        Toast.makeText(requireContext(), message ?: getString(R.string.default_error_message), Toast.LENGTH_SHORT).show()
        binding.noReposTextView.text = message
        binding.noReposTextView.isVisible = true
        binding.reposRecyclerView.isVisible = false
    }

    private fun showEmptyRepoMessage() {
        binding.noReposTextView.text = getString(R.string.no_public_repos)
        binding.noReposTextView.isVisible = true
        binding.reposRecyclerView.isVisible = false
    }

    private fun hideReposViews() {
        binding.reposRecyclerView.isVisible = false
        binding.noReposTextView.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
