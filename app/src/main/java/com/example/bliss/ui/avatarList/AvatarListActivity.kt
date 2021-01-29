package com.example.bliss.ui.avatarList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bliss.databinding.ActivityAvatarListBinding
import com.example.bliss.databinding.LayoutEmptyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvatarListActivity : AppCompatActivity() {
    private val viewModel: AvatarListViewModel by viewModels()
    private lateinit var adapter: AvatarListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAvatarListBinding.inflate(layoutInflater)
        val emptyBinding = LayoutEmptyBinding.bind(binding.root)
        setContentView(binding.root)

        with(binding) {
            adapter = AvatarListAdapter()
            adapter.setOnAvatarRemoveCallback { user ->
                viewModel.removeUser(user)
            }
            recyclerView.layoutManager = GridLayoutManager(this@AvatarListActivity, COLUMN_COUNT)
            recyclerView.adapter = adapter
        }

        viewModel.users.observe(this, Observer { users ->
            emptyBinding.groupEmpty.isVisible = users.isNullOrEmpty()
            users ?: return@Observer
            adapter.setItems(users)
        })
        viewModel.loadAvatars()
    }

    companion object {
        const val COLUMN_COUNT = 4
    }
}