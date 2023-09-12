package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

/**
 * Entry point for application.
 *
 * @param[args] Command-line arguments
 */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
