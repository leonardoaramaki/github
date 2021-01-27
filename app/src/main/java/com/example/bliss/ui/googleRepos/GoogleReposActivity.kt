package com.example.bliss.ui.googleRepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bliss.databinding.ActivityGoogleReposBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GoogleReposActivity : AppCompatActivity() {
    private val viewModel: GoogleReposViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = GoogleReposAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.repos.collectLatest { adapter.submitData(it) }
        }
    }
}