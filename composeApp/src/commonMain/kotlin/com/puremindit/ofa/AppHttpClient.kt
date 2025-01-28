package com.puremindit.ofa


import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val baseUrl = "https://ca4b8f5910d6499191f4.free.beeceptor.com/api"

class AppHttpClient {
    val config: HttpClientConfig<*>.() -> Unit = {
        expectSuccess = true
        defaultRequest {
            url("https://api.github.com")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.i { "HttpClient $message" }
                }
            }
        }
        install(ResponseObserver) {
            onResponse { response ->
                co.touchlab.kermit.Logger.d(TAG_HTTP_STATUS_LOGGER + " ${response.status.value}")
            }
        }
        install(ContentNegotiation) {
            json(json)
        }
    }


    fun createKtorClient(): HttpClient =
        HttpClient(config)


    companion object {
        fun create() = AppHttpClient().createKtorClient()
        private const val TIME_OUT = 60_000L
        private const val TAG_KTOR_LOGGER = "ktor_logger:"
        const val TAG_HTTP_STATUS_LOGGER = "http_status:"
    }
}

val json
    get() = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        prettyPrint = true
        allowSpecialFloatingPointValues = true
        allowStructuredMapKeys = true
        prettyPrint = false
        useArrayPolymorphism = false
        explicitNulls = false
        coerceInputValues = true
        // classDiscriminator = "#class" // to avoid type as default reserve discriminator
    }


val posts = listOf(
    Post(
        id = 1,
        creatorId = 101,
        caption = "Exploring the beauty of the mountains 🏔️✨",
        createdAt = "2025-01-25T14:30:00",
        likesCount = 345,
        commentsCount = 27,
        sharesCount = 10,
        viewsCount = 1200,
        isSponsored = false,
        coverURL = "https://example.com/images/mountain.jpg",
        locationName = "Rocky Mountains",
        isFavoritedByCurrentUser = true
    ),
    Post(
        id = 2,
        creatorId = 102,
        caption = "Can't start the day without coffee ☕ #morningvibes",
        createdAt = "2025-01-26T08:00:00",
        likesCount = 120,
        commentsCount = 8,
        sharesCount = 5,
        viewsCount = 400,
        isSponsored = false,
        coverURL = "https://example.com/images/coffee.jpg",
        locationName = "Downtown Cafe",
        isFavoritedByCurrentUser = false
    ),
    Post(
        id = 3,
        creatorId = 103,
        caption = null,
        createdAt = "2025-01-24T18:45:00",
        likesCount = 890,
        commentsCount = 52,
        sharesCount = 35,
        viewsCount = 3200,
        isSponsored = true,
        coverURL = "https://example.com/images/sponsored.jpg",
        locationName = null,
        isFavoritedByCurrentUser = false
    ),
    Post(
        id = 4,
        creatorId = 104,
        caption = "Sunsets are proof that no matter what happens, every day can end beautifully 🌅",
        createdAt = "2025-01-23T17:15:00",
        likesCount = 230,
        commentsCount = 15,
        sharesCount = 12,
        viewsCount = 890,
        isSponsored = false,
        coverURL = "https://example.com/images/sunset.jpg",
        locationName = "Laguna Beach",
        isFavoritedByCurrentUser = true
    ),
    Post(
        id = 5,
        creatorId = 105,
        caption = "Check out our latest deals and discounts! 🛍️",
        createdAt = "2025-01-27T10:00:00",
        likesCount = 450,
        commentsCount = 30,
        sharesCount = 20,
        viewsCount = 1500,
        isSponsored = true,
        coverURL = "https://example.com/images/deals.jpg",
        locationName = null,
        isFavoritedByCurrentUser = false
    )
)
