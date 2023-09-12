package com.example

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
internal class DataControllerTest {
    @Autowired
    private lateinit var dataController: DataController

    private val webTestClient: WebTestClient by lazy {
        WebTestClient
            .bindToController(dataController)
            .build()
    }

    @Nested
    inner class WebFluxFluxEndpoint {
        @Test
        fun `should return HTTP 200 when requesting Strings`() {
            webTestClient.get()
                .uri("/strings")
                .exchange()
                .expectStatus()
                .isOk
        }

        @Test
        fun `should return HTTP 400 when requesting Strings with error`() {
            webTestClient.get()
                .uri("/strings-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        @Test
        fun `should return HTTP 200 when requesting Data`() {
            webTestClient.get()
                .uri("/data")
                .exchange()
                .expectStatus()
                .isOk
        }

        @Test
        fun `should return HTTP 200 when streaming Data`() {
            webTestClient.get()
                .uri("/data")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isOk
        }

        @Test
        fun `should return HTTP 400 when requesting Data with error`() {
            webTestClient.get()
                .uri("/data-error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        @Test
        fun `should return HTTP 400 when streaming Data with error`() {
            webTestClient.get()
                .uri("/data-error")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isBadRequest
        }

        @Test
        fun `should return HTTP 400 when requesting error`() {
            webTestClient.get()
                .uri("/error")
                .exchange()
                .expectStatus()
                .isBadRequest
        }
    }
}
