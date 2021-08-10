package com.example.clickservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ClickServiceApplication

fun main(args: Array<String>) {
    runApplication<ClickServiceApplication>(*args)
}
