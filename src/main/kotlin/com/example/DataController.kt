package com.example

import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DataController(private val service: DataService) {

    @GetMapping("/strings")
    suspend fun getStrings(): Flow<String> = service.strings()

    @GetMapping("/strings-error")
    suspend fun stringsWithError(): Flow<String> = service.stringsWithError()

    @GetMapping("/data")
    suspend fun data(): Flow<Data> = service.data()

    @GetMapping("/data-error")
    suspend fun dataWithError(): Flow<Data> = service.dataWithError()

    @GetMapping("/error")
    suspend fun error(): Flow<Data> = service.error()
}
