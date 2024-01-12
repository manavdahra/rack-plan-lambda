package com.example

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.HttpClientConfiguration
import java.net.URI
import java.time.Duration

@Factory
class Config {
    @Bean
    fun httpClient(httpClientConfig: HttpClientConfiguration): HttpClient {
        return HttpClient.create(URI.create("/").toURL(), httpClientConfig)
    }

    @Bean
    fun httpClientConfiguration(): HttpClientConfiguration {
        val clientConfiguration = object : HttpClientConfiguration() {
            override fun getConnectionPoolConfiguration(): ConnectionPoolConfiguration {
                return ConnectionPoolConfiguration()
            }
        }
        clientConfiguration.setReadTimeout(Duration.parse("10s"))
        clientConfiguration.setConnectTimeout(Duration.parse("10s"))
        return clientConfiguration
    }
}
