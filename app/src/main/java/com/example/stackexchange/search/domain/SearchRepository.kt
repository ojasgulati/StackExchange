package com.example.stackexchange.search.domain

import com.example.stackexchange.search.model.QuestionsData
import com.example.stackexchange.search.model.SearchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchRepository {
    fun getQuestions(): Flow<QuestionsData>
}

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi,
    private val transformer: SearchTransformer
) : SearchRepository {

    override fun getQuestions(): Flow<QuestionsData> {
        return flow {
            val questions = api.getQuestions()
            val transformedQuestionsData = transformer.results(questions.items)
            emit(transformedQuestionsData)
        }.flowOn(Dispatchers.IO)
    }
}
