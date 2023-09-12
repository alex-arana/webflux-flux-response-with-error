package com.example

import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.logging.AccessLogWriter
import com.linecorp.armeria.server.logging.LoggingService
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArmeriaConfiguration {

    // A user can configure the server by providing an ArmeriaServerConfigurator bean.
    @Bean
    fun armeriaServerConfigurator(): ArmeriaServerConfigurator {
        // Customize the server using the given ServerBuilder. For example:
        return ArmeriaServerConfigurator { builder ->
            // Add DocService that enables you to send Thrift and gRPC requests
            // from web browser.
            builder.serviceUnder("/docs", DocService())

            // Log every message which the server receives and responds.
            builder.decorator(LoggingService.newDecorator())

            // Write access log after completing a request.
            builder.accessLogWriter(AccessLogWriter.combined(), false)
        }
    }
}
