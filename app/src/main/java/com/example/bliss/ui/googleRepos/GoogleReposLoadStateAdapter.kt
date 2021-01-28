package com.example.bliss.ui.googleRepos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bliss.R
import com.example.bliss.databinding.ItemLoadingBinding
import com.example.bliss.ui.googleRepos.GoogleReposLoadStateAdapter.LoadStateViewHolder

class GoogleReposLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {
    private lateinit var binding: ItemLoadingBinding

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        binding.progressLoading.isVisible = loadState is LoadState.Loading || loadState is LoadState.Error
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        binding = ItemLoadingBinding.bind(view)
        return LoadStateViewHolder(binding)
    }

    class LoadStateViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}