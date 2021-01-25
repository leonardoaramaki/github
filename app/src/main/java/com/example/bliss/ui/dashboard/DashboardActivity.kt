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
import com.example.bliss.ui.avatarlist.AvatarListActivity
import com.example.bliss.ui.emojilist.EmojiListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashboardBinding.inflate(layoutInflater)
        with(binding) {
            val context = this@DashboardActivity
            viewModel.randomEmoji.observe(context, Observer { emoji ->
                emoji ?: return@Observer
                Glide.with(this@DashboardActivity)
                    .load(emoji.url)
                    .into(ivPreview)
                progress.hide()
            })
            viewModel.user.observe(context, Observer { result ->
                progressSearch.hide()
                etUsername.isEnabled = true
                etUsername.requestFocus()
                if (result.isFailure) {
                    etUsername.text?.clear()
                    Toast.makeText(context, getString(R.string.not_found_username), Toast.LENGTH_SHORT).show()
                }
                val user = result.getOrNull() ?: return@Observer
                Glide.with(context)
                    .load(user.avatarUrl)
                    .into(ivPreview)
            })
            viewModel.loadRandomEmoji()

            btnRandomEmoji.setOnClickListener {
                viewModel.loadRandomEmoji()
            }

            btnEmojiList.setOnClickListener {
                openEmojiList()
            }

            btnAvatarList.setOnClickListener {
                openAvatarList()
            }

            btnSearch.isEnabled = false
            btnSearch.setOnClickListener {
                progressSearch.show()
                etUsername.isEnabled = false
                viewModel.searchUser(etUsername.text.toString())
            }

            etUsername.addTextChangedListener {
                btnSearch.isEnabled = !it.isNullOrEmpty()
            }

            setContentView(root)
        }
    }

    private fun openEmojiList() {
        startActivity(Intent(this, EmojiListActivity::class.java))
    }

    private fun openAvatarList() {
        startActivity(AvatarListActivity.intent(this@DashboardActivity))
    }
}