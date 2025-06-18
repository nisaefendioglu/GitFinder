package com.nisaefendioglu.gitfinder.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nisaefendioglu.gitfinder.R
import com.nisaefendioglu.gitfinder.databinding.FragmentUserListBinding
import com.nisaefendioglu.gitfinder.presentation.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels()
    private val args: UserListFragmentArgs by navArgs()
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        val searchQuery = args.searchQuery
        viewModel.searchUsers(searchQuery)
    }

    private fun setupRecyclerView() {
        userListAdapter = UserListAdapter(
            onItemClick = { user ->
                val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(user.login, user.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { user ->
                viewModel.toggleFavoriteStatus(user)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { resource ->
                    binding.progressBar.isVisible = resource is Resource.Loading
                    binding.errorText.isVisible = resource is Resource.Error
                    binding.emptyStateText.isVisible = resource is Resource.Empty

                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                userListAdapter.submitList(it)
                                binding.emptyStateText.isVisible = it.isEmpty()
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                            binding.errorText.text = resource.message
                        }
                        is Resource.Loading -> {
                        }
                        is Resource.Empty -> {
                            binding.emptyStateText.text = getString(R.string.no_results_found)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}