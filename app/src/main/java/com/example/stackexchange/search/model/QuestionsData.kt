package com.example.stackexchange.search.model


data class QuestionsData(
    val items: List<QuestionItem>?,
    val allTags: List<String>,
    val avgView: Double,
    val avgAns: Double
) {
    data class QuestionItem(
        val type: Int = 0,
        val link: String,
        val tags: List<String>?,
        val title: String,
        val ownerName: String,
        val ownerImage: String?,
        val dateCreated: String
    )
}
