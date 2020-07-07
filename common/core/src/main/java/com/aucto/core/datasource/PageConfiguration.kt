package com.aucto.core.datasource

data class PageConfiguration(
        var isPagination: Boolean = true,
        var page: Int = 12,
        var count: Int = 36
)