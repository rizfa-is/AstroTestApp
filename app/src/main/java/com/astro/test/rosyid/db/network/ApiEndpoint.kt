package com.astro.test.rosyid.db.network

import com.astro.test.rosyid.db.network.model.Developer
import com.astro.test.rosyid.db.network.model.DeveloperByName
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("users")
    suspend fun getDeveloperList(): List<Developer>

    @GET("search/users")
    suspend fun getDeveloperListByName(
        @Query("q") name: String = ""
    ): DeveloperByName
}