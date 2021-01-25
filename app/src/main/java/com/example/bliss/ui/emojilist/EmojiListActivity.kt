package com.example.bliss.ui.emojilist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bliss.R
import com.example.bliss.databinding.ActivityEmojiListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmojiListActivity : AppCompatActivity() {
    private val viewModel: EmojiListViewModel by viewModels()
    private lateinit var adapter: EmojiListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmojiListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            adapter = EmojiListAdapter()
            recyclerView.layoutManager = GridLayoutManager(this@EmojiListActivity, NUMBERS_OF_COLS)
            recyclerView.adapter = adapter
        }

        // Load emojis
        viewModel.emojiList.observe(this, Observer { emojiList ->
            emojiList ?: return@Observer
            binding.progressEmojiLoading.hide()
            adapter.setItems(emojiList)
        })
        viewModel.loadEmojis()
    }

    private companion object {
        const val NUMBERS_OF_COLS = 4
    }
}
