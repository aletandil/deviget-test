package com.projecttesting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projecttesting.data.daos.RidersDao
import com.projecttesting.data.models.Rider

@Database(entities = [Rider::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ridersDao(): RidersDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "ProjectTestingApp.db"
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