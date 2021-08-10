package com.example.clickservice.jobs

import com.example.clickservice.repository.ClickHistogramRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.ConcurrentHashMap


@Component
class HistogramCreationJob(
    val histogramRepository: ClickHistogramRepository,
    val concurrentHashMap: ConcurrentHashMap<String, Long>
) {
    @Scheduled(cron = "* * * * * *")
    fun updateHistogram() {
        if (!concurrentHashMap.isEmpty())
            histogramRepository.bulkAddition(concurrentHashMap.toMap())
        concurrentHashMap.clear()
    }
}