package com.example.stackexchange.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.databinding.LayoutBannerBinding
import com.example.stackexchange.databinding.LayoutQuestionsBinding
import com.example.stackexchange.search.model.QuestionsData
import com.squareup.picasso.Picasso

class SearchAdapter(private val clickListener: (QuestionsData.QuestionItem) -> Unit) :
    ListAdapter<QuestionsData.QuestionItem, RecyclerView.ViewHolder>(SearchDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        when (position) {
            0 -> return SearchViewHolder(
                LayoutQuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            1 -> return BannerViewHolder(
                LayoutBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        return SearchViewHolder(
            LayoutQuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as SearchViewHolder).bindTo(getItem(position))
            1 -> (holder as BannerViewHolder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }

    inner class SearchViewHolder(
        private val binding: LayoutQuestionsBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickListener(currentList[bindingAdapterPosition])
            }
        }

        fun bindTo(data: QuestionsData.QuestionItem) {
            binding.tvQuestionTitle.text = data.title
            data.ownerImage?.let {
                Picasso.get()
                    .load(it)
                    .into(binding.ivOwnerImage)
            }

            binding.tvCreatedDate.text = data.dateCreated

        }
    }

    inner class BannerViewHolder(
        binding: LayoutBannerBinding
    ) :
        RecyclerView.ViewHolder(binding.root)
}

class SearchDiffUtils : DiffUtil.ItemCallback<QuestionsData.QuestionItem>() {
    override fun areItemsTheSame(
        oldItem: QuestionsData.QuestionItem,
        newItem: QuestionsData.QuestionItem
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: QuestionsData.QuestionItem,
        newItem: QuestionsData.QuestionItem
    ): Boolean =
        oldItem == newItem
}