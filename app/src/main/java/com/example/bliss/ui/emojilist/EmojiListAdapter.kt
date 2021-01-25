package com.example.bliss.ui.emojilist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bliss.R
import com.example.bliss.data.Emoji
import com.example.bliss.databinding.ItemEmojiBinding

class EmojiListAdapter : RecyclerView.Adapter<EmojiListAdapter.EmojiViewHolder>() {
    private val emojis = mutableListOf<Emoji>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder
        = EmojiViewHolder(ItemEmojiBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(emojis[position])
        holder.itemView.setOnClickListener {
            // Remove item on click
            emojis.indexOfFirst { it.shortCode == emojis[holder.adapterPosition].shortCode }
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount() = emojis.size

    fun setItems(items: List<Emoji>) {
        emojis.clear()
        emojis.addAll(items)
        notifyDataSetChanged()
    }

    class EmojiViewHolder(private val binding: ItemEmojiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emoji: Emoji) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(emoji.url)
                    .into(ivEmoji)
            }
        }
    }
}
