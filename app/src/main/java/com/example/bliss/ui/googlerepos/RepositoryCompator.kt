package com.example.bliss.ui.googlerepos

import androidx.recyclerview.widget.DiffUtil
import com.example.bliss.data.source.Repository

class RepositoryCompator : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}
