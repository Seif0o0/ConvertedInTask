package com.convertedin.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.convertedin.task.databinding.AlbumItemBinding
import com.convertedin.task.domain.model.Album
import com.convertedin.task.presentation.utils.ListItemClickListener
import javax.inject.Inject

class AlbumsAdapter @Inject constructor() :
    ListAdapter<Album, AlbumsAdapter.ViewHolder>(AlbumsComparator) {
    private lateinit var albumClickListener: ListItemClickListener<Album>
    fun setOnClickListener(listener: ListItemClickListener<Album>) {
        albumClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), albumClickListener)
    }


    class ViewHolder private constructor(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, clickListener: ListItemClickListener<Album>) {
            binding.album = album
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    AlbumItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    object AlbumsComparator : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
    }
}