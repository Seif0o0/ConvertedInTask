package com.convertedin.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.convertedin.task.databinding.UserItemBinding
import com.convertedin.task.domain.model.User
import com.convertedin.task.presentation.utils.ListItemClickListener
import javax.inject.Inject

class UsersAdapter @Inject constructor() :
    ListAdapter<User, UsersAdapter.ViewHolder>(UsersComparator) {
    private lateinit var userClickListener: ListItemClickListener<User>
    fun setOnClickListener(listener: ListItemClickListener<User>) {
        userClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), userClickListener)
    }


    class ViewHolder private constructor(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: ListItemClickListener<User>) {
            binding.user = user
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    UserItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    object UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }
}