package com.amanda.newsfeed.data

data class Metadata(
    val adHierarchy: String,
    val attribution: Attribution,
    val mpxCategoryName: String,
    val orderLineupId: String,
    val orderLineupSlug: String,
    val ottTitle: Any,
    val pageDescription: String,
    val pageTitle: String,
    val polopolyDeptName: String,
    val polopolyExternalId: String,
    val tracking: Tracking
)