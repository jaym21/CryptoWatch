package dev.jaym21.cryptowatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jaym21.cryptoapi.models.entities.NewsData
import dev.jaym21.cryptowatch.databinding.RvNewsItemBinding

class NewsRVAdapter: ListAdapter<NewsData, NewsRVAdapter.NewsViewHolder>(NewsDiffCallback()) {

    class NewsDiffCallback: DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    inner class NewsViewHolder(val binding: RvNewsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding = RvNewsItemBinding.inflate(inflater)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.tvNewsTitle.text = currentItem.title
        Glide.with(holder.binding.root.context).load(currentItem.imageurl).into(holder.binding.ivNewsImage)
    }
}