package com.example.clickservice.repository.extra

interface ClickHistogramRepositoryExtra {
    fun bulkAddition(data : Map<String , Long>)
}