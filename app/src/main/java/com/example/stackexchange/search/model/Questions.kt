package com.example.stackexchange.search.model

data class Questions(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
) {
    data class Item(
        val accepted_answer_id: Int,
        val answer_count: Int,
        val closed_date: Int,
        val closed_reason: String,
        val content_license: String,
        val creation_date: String,
        val is_answered: Boolean,
        val last_activity_date: String,
        val last_edit_date: String,
        val link: String,
        val owner: Owner,
        val protected_date: String,
        val question_id: Int,
        val score: Int,
        val tags: List<String>?,
        val title: String,
        val view_count: Int
    ) {
        data class Owner(
            val accept_rate: Int,
            val display_name: String,
            val link: String,
            val profile_image: String?,
            val reputation: Int,
            val user_id: Int,
            val user_type: String
        )
    }
}