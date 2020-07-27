package com.example.weatherloggerapp.Repositories.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(WeatherTemp::class), version = 1, exportSchema = false)
public abstract class TempRoomDatabase : RoomDatabase() {
    abstract fun tempDao(): TempDao

    private class TempDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var tempDao = database.tempDao()

                    // Delete all content here.
//                    tempDao.deleteAll()

                    // Add sample words.
//                    var temp = WeatherTemp(null,"Hello","12/3/5")
//                    tempDao.insert(temp) // Add sample temp.
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TempRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TempRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TempRoomDatabase::class.java, "temp_database"
                )
                    .addCallback(TempDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}