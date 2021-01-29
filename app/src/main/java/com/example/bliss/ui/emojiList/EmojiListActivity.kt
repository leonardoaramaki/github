package com.example.bliss.ui.emojiList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bliss.R
import com.example.bliss.databinding.ActivityEmojiListBinding
import com.example.bliss.databinding.LayoutEmptyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmojiListActivity : AppCompatActivity() {
    private val viewModel: EmojiListViewModel by viewModels()
    private lateinit var adapter: EmojiListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmojiListBinding.inflate(layoutInflater)
        val emptyBinding = LayoutEmptyBinding.bind(binding.root)
        setContentView(binding.root)
        with(binding) {
            adapter = EmojiListAdapter()
            recyclerView.layoutManager = GridLayoutManager(this@EmojiListActivity, NUMBERS_OF_COLS)
            recyclerView.adapter = adapter
        }

        // Load emojis
        viewModel.emojiList.observe(this, Observer { result ->
            emptyBinding.groupEmpty.isVisible = result.getOrNull().isNullOrEmpty() || result.isFailure
            binding.progressEmojiLoading.hide()
            adapter.setItems(result.getOrNull().orEmpty())
        })
        viewModel.loadEmojis()

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.purple_700),
            ContextCompat.getColor(this, R.color.purple_500),
            ContextCompat.getColor(this, R.color.purple_200)
        )
        binding.swipeRefreshLayout.setColorSchemeColors(*colors)
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.clear()
            viewModel.loadEmojis(refresh = true)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private companion object {
        const val NUMBERS_OF_COLS = 4
    }
}
