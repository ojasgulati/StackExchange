package com.example.stackexchange.search.domain

import com.example.stackexchange.commons.extensitons.epochToFormatedDate
import com.example.stackexchange.search.model.Questions
import com.example.stackexchange.search.model.QuestionsData
import javax.inject.Inject

interface SearchTransformer {
    fun results(resultItems: List<Questions.Item>): QuestionsData
}

class SearchTransformerImpl @Inject constructor() : SearchTransformer {
    override fun results(resultItems: List<Questions.Item>): QuestionsData {
        val tags = mutableSetOf<String>()
        var totalAns = 0
        var totalView = 0
        val questionItem = mutableListOf<QuestionsData.QuestionItem>()
        resultItems
            .forEach {
                questionItem.add(toComponentData(result = it))
                totalAns += it.answer_count
                totalView += it.view_count
                it.tags?.let { tagList ->
                    tags.addAll(tagList)
                }
            }
        questionItem.add( //Only for testing purpose
            QuestionsData.QuestionItem(
                type = 1,
                "test",
                null,
                "text",
                "text",
                "teee",
                "eee"
            )
        )
        val avgAnswer = totalAns.toDouble() / questionItem.size.toDouble()
        val avgView = totalView.toDouble() / questionItem.size.toDouble()
        return QuestionsData(
            items = questionItem,
            allTags = tags.toList(),
            avgView = avgView,
            avgAns = avgAnswer
        )
    }

    private fun toComponentData(result: Questions.Item): QuestionsData.QuestionItem {
        val creationDateString = result.creation_date.toLong().epochToFormatedDate("dd-MM-yyyy")
        return QuestionsData.QuestionItem(
            link = result.link,
            tags = result.tags,
            title = result.title,
            ownerImage = result.owner.profile_image,
            ownerName = result.owner.display_name,
            dateCreated = creationDateString
        )
    }
}