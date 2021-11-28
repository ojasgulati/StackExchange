package com.example.stackexchange.search.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.stackexchange.R
import com.example.stackexchange.databinding.ActivitySearchBinding
import com.example.stackexchange.databinding.LayoutBottomSheetTagSelectBinding
import com.example.stackexchange.search.domain.SearchViewModel
import com.example.stackexchange.search.model.QuestionsData
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var bottomSheet: BottomSheetDialog
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)

        val adapter = SearchAdapter(::clickItem)
        activitySearchBinding.rvQuestionsCard.adapter = adapter
        viewModel.questionsLiveData.observe(this) {
            it?.let {
                adapter.submitList(it.items)
                setupFilterBottomSheet(it.allTags)
                setupAvgViews(it)
            }
        }

        activitySearchBinding.svQuestions.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filter(newText)
                return false
            }
        })

    }

    private fun clickItem(data: QuestionsData.QuestionItem) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.link)))
    }

    private fun setupFilterBottomSheet(tags: List<String>) {
        with(BottomSheetDialog(this)) {
            bottomSheet = this
            val binding = LayoutBottomSheetTagSelectBinding.inflate(layoutInflater, null, false)
            setContentView(binding.root)
            val tagAdapter = TagAdapter(::tagSelect)
            tagAdapter.submitList(tags)
            binding.rvTagSelect.adapter = tagAdapter
            activitySearchBinding.ivFilter.setOnClickListener {
                show()
            }
        }

    }

    private fun tagSelect(selectedTag: String) {
        activitySearchBinding.svQuestions.setQuery("", false)
        viewModel.filterByTags(selectedTag)
        bottomSheet.hide()
    }

    private fun setupAvgViews(data: QuestionsData) {
        if (data.items == null || data.items.isEmpty()) {
            activitySearchBinding.tvAvgAnsCount.visibility = View.GONE
            activitySearchBinding.tvAvgViewCount.visibility = View.GONE
        } else {
            activitySearchBinding.tvAvgAnsCount.visibility = View.VISIBLE
            activitySearchBinding.tvAvgViewCount.visibility = View.VISIBLE
            activitySearchBinding.tvAvgAnsCount.text = "${getString(R.string.average_answer_count)} ${String.format("%.2f",data.avgAns)}"
            activitySearchBinding.tvAvgViewCount.text = "${getString(R.string.average_view_count)} ${String.format("%.2f",data.avgView)}"
        }
    }

}