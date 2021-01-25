package com.example.bliss.ui.googlerepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bliss.databinding.ActivityGoogleReposBinding
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.repositoryPagedList.observe(this, Observer { repos ->
            adapter.submitList(repos)
        })
    }
}