package com.example.bliss.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bliss.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashboardBinding.inflate(layoutInflater)

        viewModel.randomEmoji.observe(this, Observer { emoji ->
            emoji ?: return@Observer
            Glide.with(this)
                .load(emoji.url)
                .into(binding.ivEmoji)
            binding.progress.hide()
        })

        viewModel.loadRandomEmoji()

        binding.btnRandomEmoji.setOnClickListener { viewModel.loadRandomEmoji() }

        setContentView(binding.root)
    }
}