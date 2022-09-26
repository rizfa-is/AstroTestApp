package com.astro.test.rosyid.db.local

import androidx.room.*
import com.astro.test.rosyid.db.network.model.Developer

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(developer: Developer)

    @Delete
    suspend fun deleteFavorite(developer: Developer)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorite()

    @Query("SELECT * FROM favorites_table")
    suspend fun getFavorite(): List<Developer>
}