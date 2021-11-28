package com.example.stackexchange.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.databinding.LayoutTagSelectBinding

class TagAdapter(private val clickListener: (tag: String) -> Unit) :
    ListAdapter<String, TagAdapter.TagViewHolder>(TagDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TagViewHolder {
        return TagViewHolder(
            LayoutTagSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class TagViewHolder(
        private val binding: LayoutTagSelectBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(tag: String) {
            binding.tvTag.text = tag
            binding.root.setOnClickListener {
                clickListener(tag)
            }
        }
    }
}

class TagDiffUtils : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }


}