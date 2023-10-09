package com.convertedin.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.convertedin.task.databinding.PhotoItemBinding
import com.convertedin.task.domain.model.Photo
import com.convertedin.task.presentation.utils.ListItemClickListener
import javax.inject.Inject

class PhotosAdapter @Inject constructor() :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotosComparator) {
    private lateinit var photoClickListener: ListItemClickListener<Photo>
    fun setOnClickListener(listener: ListItemClickListener<Photo>) {
        photoClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), photoClickListener)
    }


    class ViewHolder private constructor(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, clickListener: ListItemClickListener<Photo>) {
            binding.photo = photo
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    PhotoItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    object PhotosComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }
}