package com.projecttesting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projecttesting.data.daos.EntriesDao
import com.projecttesting.data.models.Entry

@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun entriesDao(): EntriesDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "ProjectTesting.db"
                    )
                        .build()
                }
                return INSTANCE as AppDatabase
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}