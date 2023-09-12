package com.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class DataController(private val service: DataService) {

    @GetMapping("/strings")
    fun getStrings(): Flux<String> = service.strings()

    @GetMapping("/strings-error")
    fun stringsWithError(): Flux<String> = service.stringsWithError()

    @GetMapping("/data")
    fun data(): Flux<Data> = service.data()

    @GetMapping("/data-error")
    fun dataWithError(): Flux<Data> = service.dataWithError()

    @GetMapping("/error")
    fun error(): Flux<Data> = service.error()
}
