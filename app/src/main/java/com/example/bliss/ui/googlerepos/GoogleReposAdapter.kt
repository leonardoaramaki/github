package com.example.bliss.ui.googlerepos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bliss.data.source.Repository
import com.example.bliss.databinding.ItemRepositoryBinding

class GoogleReposAdapter : PagedListAdapter<Repository, GoogleReposAdapter.RepoViewHolder>(RepositoryCompator()) {
    private val repositories = mutableListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let { holder.bind(repo) }
    }

    class RepoViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.tvName.text = repository.fullName
        }
    }
}
