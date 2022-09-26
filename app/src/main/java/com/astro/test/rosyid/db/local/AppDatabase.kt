package com.astro.test.rosyid.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.astro.test.rosyid.db.network.model.Developer

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Developer::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "astro_test_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE!!
        }
    }
}