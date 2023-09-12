package com.example

import io.kotest.core.spec.style.ExpectSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
internal class DataControllerTest(private val webTestClient: WebTestClient) : ExpectSpec({

    context("WebFlux Flux Endpoint") {
        expect("should return HTTP 200 when requesting Strings") {
            webTestClient.get()
                .uri("/strings")
                .exchange()
                .expectStatus()
                .isOk
        }

        expect("should return HTTP 400 when requesting Strings with error") {
            webTestClient.get()
                .uri("/strings-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        expect("should return HTTP 200 when requesting Data") {
            webTestClient.get()
                .uri("/data")
                .exchange()
                .expectStatus()
                .isOk
        }

        expect("should return HTTP 400 when requesting Data with error") {
            webTestClient.get()
                .uri("/data-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        expect("should return HTTP 400 when requesting error") {
            webTestClient.get()
                .uri("/error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }
    }
})
