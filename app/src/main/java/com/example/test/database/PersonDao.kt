package com.example.test.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personObject: PersonObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(personList: List<PersonObject>)

    @Delete
    fun delete(personObject: PersonObject)

    @Query("DELETE FROM person")
    fun deleteAll()

    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<PersonObject>>
}