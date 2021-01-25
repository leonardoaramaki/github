package com.example.bliss.ui.emojilist

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.bliss.data.Emoji
import com.example.bliss.databinding.ItemImageBinding

class EmojiListAdapter : RecyclerView.Adapter<EmojiListAdapter.EmojiViewHolder>() {
    private val emojis = mutableListOf<Emoji>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder =
        EmojiViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(emojis[position])
        holder.itemView.setOnClickListener {
            // Remove item on click
            val index =
                emojis.indexOfFirst { it.shortCode == emojis[holder.adapterPosition].shortCode }
            emojis.removeAt(index)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount() = emojis.size

    fun setItems(items: List<Emoji>) {
        emojis.clear()
        emojis.addAll(items)
        notifyDataSetChanged()
    }

    class EmojiViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(emoji: Emoji) {
            with(binding) {
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_RGB_565)
                Glide.with(itemView.context)
                    .load(emoji.url)
                    .override(EMOJI_SIZE, EMOJI_SIZE)
                    .centerInside()
                    .apply(options)
                    .into(ivImage)
            }
        }

        companion object {
            val EMOJI_SIZE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                128f,
                Resources.getSystem().displayMetrics
            ).toInt()
        }
    }
}
