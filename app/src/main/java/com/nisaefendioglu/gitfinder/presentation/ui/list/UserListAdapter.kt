package com.nisaefendioglu.gitfinder.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nisaefendioglu.gitfinder.R
import com.nisaefendioglu.gitfinder.databinding.ItemUserBinding
import com.nisaefendioglu.gitfinder.domain.model.GithubUser

class UserListAdapter(
    private val onItemClick: (GithubUser) -> Unit,
    private val onFavoriteClick: (GithubUser) -> Unit
) : ListAdapter<GithubUser, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.favoriteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavoriteClick(getItem(position))
                }
            }
        }

        fun bind(user: GithubUser) {
            binding.userNameTextView.text = user.login
            Glide.with(binding.userAvatarImageView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_default_avatar)
                .circleCrop()
                .into(binding.userAvatarImageView)
            val favoriteIconRes = if (user.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            binding.favoriteButton.setImageResource(favoriteIconRes)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem == newItem
        }
    }
}