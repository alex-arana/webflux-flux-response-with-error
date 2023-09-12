package com.example

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class DataService {

    fun strings(): Flow<String> {
        return flow {
            emit("first")
            emit("second")
        }
    }

    fun data(): Flow<Data> {
        return strings().map { Data(it) }
    }

    fun stringsWithError(): Flow<String> {
        return strings().map { value: String ->
            when (value) {
                "second" -> throw ResponseStatusException(HttpStatus.BAD_REQUEST)
                else -> value
            }
        }
    }

    fun dataWithError(): Flow<Data> {
        return stringsWithError().map { Data(it) }
    }

    fun error(): Flow<Data> {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }
}
