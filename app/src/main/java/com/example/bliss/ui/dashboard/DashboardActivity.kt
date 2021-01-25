package com.example.bliss.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bliss.databinding.ActivityDashboardBinding
import com.example.bliss.ui.emojilist.EmojiListActivity
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
        binding.btnEmojiList.setOnClickListener { showEmojiList() }

        setContentView(binding.root)
    }

    private fun showEmojiList() {
        startActivity(Intent(this, EmojiListActivity::class.java))
    }
}