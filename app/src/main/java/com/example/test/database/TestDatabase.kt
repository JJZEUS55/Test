package com.example.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PersonObject::class],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase: RoomDatabase() {

    abstract val personDao: PersonDao

    companion object{
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase?{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context, TestDatabase::class.java, "test_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}