package com.example

import io.kotest.core.spec.style.ExpectSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
internal class DataControllerTest(
    @LocalServerPort private val serverPort: Int,
    private val webTestClient: WebTestClient
) : ExpectSpec({

    context("WebFlux Flux Endpoint") {
        expect("should return HTTP 200 when requesting Strings") {
            webTestClient.get()
                .uri("http://127.0.0.1:$serverPort/strings")
                .exchange()
                .expectStatus()
                .isOk
        }

        expect("should return HTTP 400 when requesting Strings with error") {
            webTestClient.get()
                .uri("http://127.0.0.1:$serverPort/strings-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        expect("should return HTTP 200 when requesting Data") {
            webTestClient.get()
                .uri("http://127.0.0.1:$serverPort/data")
                .exchange()
                .expectStatus()
                .isOk
        }

        expect("should return HTTP 200 when streaming Data") {
            webTestClient.get()
                .uri("/data")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isOk
        }

        expect("should return HTTP 400 when requesting Data with error") {
            webTestClient.get()
                .uri("http://127.0.0.1:$serverPort/data-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        expect("should return HTTP 400 when streaming Data with error") {
            webTestClient.get()
                .uri("/data-error")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        expect("should return HTTP 400 when requesting error") {
            webTestClient.get()
                .uri("http://127.0.0.1:$serverPort/error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }
    }
})
