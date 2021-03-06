package com.example.clickservice.repository

import com.example.clickservice.repository.entity.ClickHistogram
import com.example.clickservice.repository.extra.ClickHistogramRepositoryExtra
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface ClickHistogramRepository : MongoRepository<ClickHistogram, String> , ClickHistogramRepositoryExtra