package com.example.bliss.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bliss.databinding.ActivityDashboardBinding
import com.example.bliss.ui.emojilist.EmojiListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashboardBinding.inflate(layoutInflater)
        with(binding) {
            viewModel.randomEmoji.observe(this@DashboardActivity, Observer { emoji ->
                emoji ?: return@Observer
                Glide.with(this@DashboardActivity)
                    .load(emoji.url)
                    .into(ivPreview)
                progress.hide()
            })
            viewModel.user.observe(this@DashboardActivity, Observer { user ->
                user ?: return@Observer
                Glide.with(this@DashboardActivity)
                    .load(user.avatarUrl)
                    .into(ivPreview)
            })
            viewModel.loadRandomEmoji()

            btnRandomEmoji.setOnClickListener {
                viewModel.loadRandomEmoji()
            }

            btnEmojiList.setOnClickListener {
                showEmojiList()
            }

            btnSearch.isEnabled = false
            btnSearch.setOnClickListener {
                viewModel.searchUser(etUsername.text.toString())
            }

            etUsername.addTextChangedListener {
                btnSearch.isEnabled = !it.isNullOrEmpty()
            }

            setContentView(root)
        }
    }

    private fun showEmojiList() {
        startActivity(Intent(this, EmojiListActivity::class.java))
    }
}