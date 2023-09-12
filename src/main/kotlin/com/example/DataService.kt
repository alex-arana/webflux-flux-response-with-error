package com.example

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DataService {

    fun strings(): Flux<String> {
        return Flux.just("first", "second")
    }

    fun data(): Flux<Data> {
        return strings().map { Data(it) }
    }

    fun stringsWithError(): Flux<String> {
        return strings().flatMap { value ->
            when (value) {
                "second" -> Mono.error { ResponseStatusException(HttpStatus.BAD_REQUEST) }
                else -> value.toMono()
            }
        }
    }

    fun dataWithError(): Flux<Data> {
        return stringsWithError().map { Data(it) }
    }

    fun error(): Flux<Data> {
        return Flux.error { (ResponseStatusException(HttpStatus.BAD_REQUEST)) }
    }
}
