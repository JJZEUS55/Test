package com.example.test.ui.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.database.PersonObject
import com.example.test.database.TestDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel(application: Application) : AndroidViewModel(application){

    private val database = TestDatabase.getInstance(application.applicationContext)
    val livePersons: LiveData<List<PersonObject>>? = database?.personDao?.getAll()

    fun insertList(){
        val person1 = PersonObject(0, "Miguel Cervantes", "08-Dic-1990", "Desarrollador")
        val person2 = PersonObject(0, "Juan Morales", "03-Jul-1990", "Desarrollador")
        val person3 = PersonObject(0, "Roberto Méndez", "14-Oct-1990", "Desarrollador")
        val person4 = PersonObject(0, "Miguel Cuevas", "08-Dic-1990", "Desarrollador")

        val listPerson = arrayListOf(
            person1, person2, person3, person4
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database?.personDao?.deleteAll()
                database?.personDao?.insertList(listPerson)
            }
        }
    }

}