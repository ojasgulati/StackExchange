package com.example.stackexchange.search.domain

import androidx.lifecycle.*
import com.example.stackexchange.search.model.QuestionsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private lateinit var _originalData: QuestionsData

    private val _questionsMutableLiveData = MutableLiveData<QuestionsData>()
    val questionsLiveData: LiveData<QuestionsData>
        get() = _questionsMutableLiveData

    init {
        viewModelScope.launch {
            searchRepository.getQuestions().collect {
                _questionsMutableLiveData.value = it
                _originalData = it
            }
        }
    }

    fun filter(query: String) {
        if (query.isEmpty() || query.length < 3) {
            _questionsMutableLiveData.value = _originalData
            return
        }
        val filteredList = mutableListOf<QuestionsData.QuestionItem>()
        _questionsMutableLiveData.value?.items?.forEach {
            if (it.title.contains(query, true)) filteredList.add(it)
        }
        _questionsMutableLiveData.value =
            QuestionsData(
                items = filteredList,
                allTags = _originalData.allTags,
                avgView = _originalData.avgView,
                avgAns = _originalData.avgAns
            )

    }

    fun filterByTags(selectedTag: String) {
        val filteredList = mutableListOf<QuestionsData.QuestionItem>()
        _originalData.items?.forEach {
            it.tags?.let { tags ->
                for (tag in tags)
                    if (selectedTag == tag) {
                        filteredList.add(it)
                        break
                    }
            }
        }
        _questionsMutableLiveData.postValue(
            QuestionsData(
                items = filteredList,
                allTags = _originalData.allTags,
                avgView = _originalData.avgView,
                avgAns = _originalData.avgAns
            )
        )
    }


}