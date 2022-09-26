package com.astro.test.rosyid.db.network

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientInstance {
    class Builder {
        private val loggingInterceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        fun buildToken(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val newRequestBuilder = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "token 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"
                        )
                        .addHeader(
                            "User-Agent",
                            "request"
                        )
                        .build()
                    val response = chain.proceed(newRequestBuilder)
                    val rawJson = response.body?.string()
                    val build = response.newBuilder()
                        .body(ResponseBody.create(response.body?.contentType(), rawJson!!))
                        .build()
                    response.close()

                    return@addInterceptor build
                }
                .connectTimeout(400, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)

            return okHttpClientBuilder.build()
        }
    }
}