package com.example.test.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "person")
data class PersonObject (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var name: String = "",

    var birthDate: String = "",

    var position: String = ""
)