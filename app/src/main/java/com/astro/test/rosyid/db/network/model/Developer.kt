package com.astro.test.rosyid.db.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Developer(
    @PrimaryKey val id: Int,
    val login: String,
    val avatar_url: String,
    val url: String,
    val favorite: Boolean = false
)

data class DeveloperByName(
    val items: List<Developer>
)