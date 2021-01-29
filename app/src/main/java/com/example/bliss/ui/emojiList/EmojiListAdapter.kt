package com.example.bliss.ui.emojiList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.bliss.R
import com.example.bliss.data.Emoji
import com.example.bliss.databinding.ItemEmojiBinding

class EmojiListAdapter : RecyclerView.Adapter<EmojiListAdapter.EmojiViewHolder>() {
    private val emojis = mutableListOf<Emoji>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder =
        EmojiViewHolder(ItemEmojiBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(emojis[position])
        holder.itemView.setOnClickListener {
            // Remove item on click
            val index =
                emojis.indexOfFirst { it.shortCode == emojis[holder.bindingAdapterPosition].shortCode }
            emojis.removeAt(index)
            notifyItemRemoved(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount() = emojis.size

    fun setItems(items: List<Emoji>) {
        emojis.clear()
        emojis.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        emojis.clear()
        notifyDataSetChanged()
    }

    class EmojiViewHolder(private val binding: ItemEmojiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(emoji: Emoji) {
            val size = binding.root.resources.getDimensionPixelSize(R.dimen.emoji_size)
            with(binding) {
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_RGB_565)
                Glide.with(itemView.context)
                    .load(emoji.url)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .override(size, size)
                    .apply(options)
                    .into(ivGridEmoji)
            }
        }
    }
}
