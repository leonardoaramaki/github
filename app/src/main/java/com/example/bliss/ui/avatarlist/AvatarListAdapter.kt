package com.example.bliss.ui.avatarlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bliss.data.User
import com.example.bliss.databinding.ItemImageBinding

class AvatarListAdapter : RecyclerView.Adapter<AvatarListAdapter.AvatarViewHolder>() {
    private val users = mutableListOf<User>()
    private var removeAvatarCallback: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder =
        AvatarViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            // Remove item on click
            val index = users.indexOfFirst { it.id == users[holder.adapterPosition].id }
            val removed = users.removeAt(index)
            removeAvatarCallback?.invoke(removed)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount() = users.size

    fun setItems(items: List<User>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnAvatarRemoveCallback(callback: (User) -> Unit) {
        removeAvatarCallback = callback
    }

    class AvatarViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(ivImage)
            }
        }
    }
}
