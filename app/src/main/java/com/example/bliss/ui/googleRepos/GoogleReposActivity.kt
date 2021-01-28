package com.example.bliss.ui.googleRepos

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bliss.databinding.ActivityGoogleReposBinding
import com.example.bliss.databinding.LayoutEmptyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GoogleReposActivity : AppCompatActivity() {
    private val viewModel: GoogleReposViewModel by viewModels()
    private val adapter = GoogleReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        val emptyBinding = LayoutEmptyBinding.bind(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Check for errors in any of the LoadStates.
        adapter.addLoadStateListener {
            val errorRefresh = it.refresh is LoadState.Error
            val errorAppend = it.append is LoadState.Error
            // Display empty state if we have no cache and failed at initial load.
            emptyBinding.groupEmpty.isVisible = adapter.itemCount == 0 && errorRefresh
        }

        lifecycleScope.launch {
            viewModel.repos.collectLatest {
                adapter.submitData(it)
            }
        }

        setContentView(binding.root)
    }
}