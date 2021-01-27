package com.example.bliss.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bliss.R
import com.example.bliss.databinding.ActivityDashboardBinding
import com.example.bliss.ui.avatarList.AvatarListActivity
import com.example.bliss.ui.emojiList.EmojiListActivity
import com.example.bliss.ui.googleRepos.GoogleReposActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        initRandomEmojiFeature()
        initAvatarSearchFeature()
        initGoogleRepositoryListFeature()

        binding.btnEmojiList.setOnClickListener {
            openEmojiList()
        }

        setContentView(binding.root)
    }

    private fun initRandomEmojiFeature() {
        viewModel.randomEmoji.observe(this, Observer { result ->
            result ?: return@Observer
            if (result.isFailure || result.getOrNull() == null) {
                Toast.makeText(this, "Error while trying to get emojis", Toast.LENGTH_SHORT).show()
            } else {
                val emoji = result.getOrNull()
                Glide.with(this)
                    .load(emoji?.url)
                    .into(binding.ivPreview)
            }
            binding.progress.hide()
        })

        binding.btnRandomEmoji.setOnClickListener {
            binding.progress.show()
            viewModel.loadRandomEmoji()
        }

        viewModel.loadRandomEmoji()
    }

    private fun initAvatarSearchFeature() {
        binding.btnAvatarList.setOnClickListener {
            openAvatarList()
        }

        binding.btnSearch.isEnabled = false
        binding.btnSearch.setOnClickListener {
            binding.progressSearch.show()
            binding.etUsername.isEnabled = false
            viewModel.searchUser(binding.etUsername.text.toString())
        }

        binding.etUsername.addTextChangedListener {
            binding.btnSearch.isEnabled = !it.isNullOrEmpty()
        }
    }

    private fun initGoogleRepositoryListFeature() {
        viewModel.user.observe(this, Observer { result ->
            binding.progressSearch.hide()
            binding.etUsername.isEnabled = true
            binding.etUsername.requestFocus()
            if (result.isFailure) {
                binding.etUsername.text?.clear()
                Toast.makeText(this, getString(R.string.not_found_username), Toast.LENGTH_SHORT)
                    .show()
            }
            val user = result.getOrNull() ?: return@Observer
            Glide.with(this)
                .load(user.avatarUrl)
                .into(binding.ivPreview)
        })

        binding.btnGoogleRepos.setOnClickListener {
            openGoogleRepoList()
        }
    }

    private fun openEmojiList() {
        startActivity(Intent(this, EmojiListActivity::class.java))
    }

    private fun openAvatarList() {
        startActivity(Intent(this, AvatarListActivity::class.java))
    }

    private fun openGoogleRepoList() {
        startActivity(Intent(this, GoogleReposActivity::class.java))
    }
}