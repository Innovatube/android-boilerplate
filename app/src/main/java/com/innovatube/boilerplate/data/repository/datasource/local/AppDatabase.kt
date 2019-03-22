package com.innovatube.boilerplate.data.repository.datasource.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity

@Database(entities = [HeaderEntity::class], version = 5)
@TypeConverters(
    HeaderTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleFeatureDao(): HeaderDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "iStyle.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
